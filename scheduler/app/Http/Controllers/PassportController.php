<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\User;
use Validator;
use Illuminate\Support\Facades\DB;
use App\Helpers\ApiResponse;

class PassportController extends Controller
{
    public function register (Request $request)
    {

        //$request = json_decode($request->getContent(), true);
        $rules = [
            'name' => 'required|min:3|alpha_dash|unique:users',
            'email' => 'required|email|unique:users',
            'password' => 'required|min:6',
            'type' => 'required|in:Student',
        ];
        $validator = Validator::make($request->all(), $rules);
        if ($validator->fails()) {
            return  response()->json($validator->errors(), 400);
        }

        try {
           DB::beginTransaction();
            $user = User::create([
                'name' => $request->name,
                'email' => $request->email,
                'password' => bcrypt($request->password),
                'type' => $request->type,
            ]);
            $token = $user->createToken('TutsForWeb')->accessToken;
            $user->save();
           DB::commit();
            return response()->json(['token' => $token], 201);
       } catch (\Exception $e) {
           DB::rollback();
        }
    }
    
    public function login(Request $request)
    {
        $credentials = [
            'email' => $request->email,
            'password' => $request->password
        ];

        if (auth()->attempt($credentials)) {
            $token = auth()->user()->createToken('Personal Access Token')->accessToken;
            return response()->json(['token' => $token], 200);
        }
        else {
            return response()->json(['data' => 'UnAuthorised User'], 400);
        }
    }

    public function logout(Request $request)
    {
        $request->user()->token()->revoke();
        return response()->json([
            'message' => 'Successfully logged out'
        ]);
    }



}
