<?php

use Illuminate\Database\Seeder;

class RoomsTableSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        DB::table('rooms')->insert([
            'building' => 'B',
            'floor' => '1',
            'number' => '1',
        ]);
        DB::table('rooms')->insert([
            'building' => 'C',
            'floor' => '1',
            'number' => '1',
        ]);
        DB::table('rooms')->insert([
            'building' => 'D',
            'floor' => '1',
            'number' => '1',
        ]);
    }
}
