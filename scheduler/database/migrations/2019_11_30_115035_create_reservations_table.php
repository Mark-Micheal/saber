<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreateReservationsTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('reservations', function (Blueprint $table) {
            $table->bigIncrements('id');
            $table->unsignedBigInteger('room_id');
            $table->unsignedBigInteger('student_id');
            $table->Enum('day',['Saturday','Sunday','Monday','Tuesday','Wednesday','Thursday']);
            $table->enum('slot',['1st','2nd','3rd','4th','5th','After Hours']);
            $table->text('additional_info');
            $table->boolean('tutorial');
            $table->timestamps();

            //$table->primary(['room_id','day','slot']);
            $table->foreign('room_id')->references('id')->on('rooms')->onDelete('cascade');
            $table->foreign('student_id')->references('id')->on('users')->onDelete('cascade');
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('reservations');
    }
}
