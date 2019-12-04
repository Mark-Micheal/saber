<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Room extends Model
{
    protected $fillable = [
        'building', 'floor', 'number',
    ];

    public static function getAllRooms()
    {
        $rooms = Room::all();
        return response()->json($rooms, 200);
    }

    public static function showRoom(Room $room)
    {
        $room = Room::findOrFail($room);
        return response()->json($room, 200);
    }

}
