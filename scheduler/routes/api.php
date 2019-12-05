<?php

use Illuminate\Http\Request;

/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| is assigned the "api" middleware group. Enjoy building your API!
|
*/

Route::post('/register','PassportController@register');
Route::post('/login','PassportController@login');
Route::post('/logout','PassportController@logout');

Route::middleware('auth:api')->group(function () {
    
});

Route::resource('/rooms','RoomController');

Route::resource('/reservations','ReservationController');
