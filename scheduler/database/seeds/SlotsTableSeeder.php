<?php

use Illuminate\Database\Seeder;

class SlotsTableSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        DB::table('slots')->insert([
            'slot'=>'1st',
        ]);
        DB::table('slots')->insert([
            'slot'=>'2nd',
        ]);
        DB::table('slots')->insert([
            'slot'=>'3rd',
        ]);
        DB::table('slots')->insert([
            'slot'=>'4th',
        ]);
        DB::table('slots')->insert([
            'slot'=>'5th',
        ]);
        DB::table('slots')->insert([
            'slot'=>'After Hours',
        ]);
    }
}
