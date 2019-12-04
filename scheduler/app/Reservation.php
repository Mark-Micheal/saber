<?php

namespace App;

use Illuminate\Database\Eloquent\Model;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;

class Reservation extends Model
{
    protected $fillable = [
        'room_id', 'student_id', 'day', 'slot','additional_info'
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
        $room_id = $request->input('room_id');
        $student_id = $request->input('student_id');
        $day = $request->input('day');
        $slot = $request->input('slot');
        $additional_info = $request->input('additional_info');

        // Before adding, check that there is no reservation with the given room_id, date, and slot
        //try{
            DB::beginTransaction();
    //['room_id' => $room_id, 'student_id' => $student_id, 'day' => $day, 'slot' => $slot, 'additional_info' => $additional_info]
            $reservation = Reservation::create(['room_id' => $room_id, 'student_id' => $student_id, 'day' => $day, 'slot' => $slot, 'additional_info' => $additional_info]);
            $reservation->save();
            DB::commit();
            return response()->json($reservation, 200);
        //}
        // catch (\Exception $e) {
        //     DB::rollback();
        // }

        // $reservation = DB::table('reservations')->insertOrIgnore([
        //     ['room_id' => $room_id, 'day' => $day, 'slot' => $slot, 'additional_info' => $additional_info]
        // ]); // used to do many insertions in DB
       
    }

}
