<?php

namespace App;

use Illuminate\Database\Eloquent\Model;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;

class Reservation extends Model
{
    protected $fillable = [
        'room_id', 'student_id', 'day', 'slot','additional_info','tutorial'
    ];

    public static function getAllReservations()
    {
        $reservations = Reservation::all();
        return response()->json($reservations, 200);
    }

    public static function showReservation(Reservation $reservation)
    {
        $reservation = Reservation::findOrFail($reservation);
        return response()->json($reservation, 200);
    }

    public static function addReservation(Request $request)
    {
        // Getting Room id from room location
        $room_building = $request->input('building');
        $room_floor = $request->input('floor');
        $room_number = $request->input('number');
        $room_id = DB::table('rooms')->select('id')->where([
            ['rooms.building','=',$room_building],
            ['rooms.floor','=',$room_floor],
            ['rooms.number','=',$room_number],
        ])->get()->first()->id;

        // Getting id of logged in user
        $student_id = auth()->user()->id;
        
        // Getting reservation data
        $day = $request->input('day');
        $slot = $request->input('slot');
        $additional_info = $request->input('additional_info');

        // Before adding, check that there is no reservation with the given room_id, date, and slot       
        $taken_reservation = DB::table('reservations')->select('*')->where([
            ['reservations.day','=',$day],
            ['reservations.room_id','=',$room_id],
            ['reservations.slot','=',$slot],
        ])->get();

        if($taken_reservation->count() > 0) {
            return response()->json(['data'=>'This slot is already reserved'], 400);;
        }
        else { 
            if(auth()->user()->type == 'Student') // whether it is a tutorial or reservable slot
                $tutorial = 0;
            else
                $tutorial = 1;

            try {
                DB::beginTransaction();
                $reservation = Reservation::create(['room_id' => $room_id, 'student_id' => $student_id, 'day' => $day, 'slot' => $slot, 'additional_info' => $additional_info, 'tutorial' => $tutorial]);
                $reservation->save();
                DB::commit();
                return response()->json($reservation, 200);
            }
            catch (\Exception $e) {
                DB::rollback();
            }
    
            // $reservation = DB::table('reservations')->insertOrIgnore([
            //     ['room_id' => $room_id, 'day' => $day, 'slot' => $slot, 'additional_info' => $additional_info]
            // ]); // used to do many insertions in DB 
        }
          
    }

    public static function myReservations()
    {
        $student_id = auth()->user()->id;
        try {
            DB::beginTransaction();
            $reservations = DB::table('reservations')->select('*')->where('reservations.student_id','=',$student_id);
            $reservations = DB::table('rooms')->joinSub($reservations, 'reservations', function ($join){
                $join->on('reservations.room_id','=','rooms.id');
            })->get();
            DB::commit();
            return response()->json($reservations, 200);
        }
        catch (\Exception $e) {
            DB::rollback();
        }
    }

    public static function dayReservations(Request $request)
    {
        $room_building = $request->input('building');
        $room_floor = $request->input('floor');
        $room_number = $request->input('number');
        
        $room_id = DB::table('rooms')->select('id')->where([
            ['rooms.building','=',$room_building],
            ['rooms.floor','=',$room_floor],
            ['rooms.number','=',$room_number],
        ])->get()->first()->id;

        $day = $request->input('day');
        try {
            DB::beginTransaction();
            $reservations =  DB::table('reservations')->select('*')->where([
                ['reservations.room_id','=',$room_id],
                ['reservations.day','=',$day],
            ])->get();
            DB::commit();
            return response()->json($reservations, 200);
        }
        catch (\Exception $e) {
            DB::rollback();
        }
    }

    public static function freeReservations(Request $request)
    {
        $day = $request->input('day');
        $idXslots = DB::table('slots')->crossJoin('rooms')->select('rooms.id as room_id','slot');
        $reservation = DB::table('reservations')->select('reservations.room_id','slot')->where('reservations.day','=',$day)->get();
       
        $free = DB::select(DB::raw("SELECT *
                                    FROM 
                                    (SELECT rooms.id AS room_id, slot
                                    FROM rooms CROSS JOIN slots
                                    EXCEPT 
                                    SELECT reservations.room_id, slot
                                    FROM reservations
                                    WHERE reservations.day = :day) as free
                                    JOIN rooms 
                                    ON rooms.id = free.room_id
                                    "), array(
                                        'day' => $day,
                                    ));
    
        
        return response()->json($free, 200);

        
    }

    public static function clearReservations(Request $request)
    {
        $day = $request->input('day');
        $deleted = Reservation::where('day',$day)->delete();
        return response()->json($deleted, 200);
    }

}
