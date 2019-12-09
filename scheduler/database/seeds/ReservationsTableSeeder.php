<?php

use Illuminate\Database\Seeder;

class ReservationsTableSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        $student_id = DB::table('users')->select('id')->where('users.name','=','Adminy')->get()->first()->id;
        $building_id = DB::table('rooms')->select('id')->where('rooms.building','=','B');
        $floor_id = DB::table('rooms')->select('id')->where('rooms.floor','=','1');
        $room_id = DB::table('rooms')->select('id')->where('rooms.number','=','1')->union($building_id)->union($floor_id)->get()->first()->id;
        
        DB::table('reservations')->insert([
            'room_id' => $room_id,
            'student_id' => $student_id,
            'day' => 'Monday', // now()->format( 'l' )
            'slot' => '2nd',
            'additional_info'=> 'This is a very first reservation',
            'tutorial'=> true,
            'created_at'=> now(),
            'updated_at'=> now(),
        ]);

        DB::table('reservations')->insert([
            'room_id' => $room_id,
            'student_id' => $student_id,
            'day' => 'Tuesday', // now()->format( 'l' )
            'slot' => '2nd',
            'additional_info'=> 'This is a very first reservation',
            'tutorial'=> true,
            'created_at'=> now(),
            'updated_at'=> now(),
        ]);

        DB::table('reservations')->insert([
            'room_id' => $room_id,
            'student_id' => $student_id,
            'day' => 'Wednesday', // now()->format( 'l' )
            'slot' => '2nd',
            'additional_info'=> 'This is a very first reservation',
            'tutorial'=> true,
            'created_at'=> now(),
            'updated_at'=> now(),
        ]);
    }
}
