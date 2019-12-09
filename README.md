# saber

A smart reservation system to help students find convenient location to work on their projects

## Getting Started
These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

* [Git](https://git-scm.com/book/en/v2/Getting-Started-Installing-Git) - Install git cli
* [Docker](https://docs.docker.com/install/) - Install docker
* [AndroidStudio](https://developer.android.com/studio) - Install android studio to launch the app

### Installing

First clone the repo
```git
git clone https://github.com/Mark-Micheal/saber.git
```

Then replace the env.example with .env and write your database credentials that you will use

Then run docker comand to get the app running
```docker
docker-compose up --build
```
visit `http://localhost:8100/` , you will see the laravel homepage.

It's that easy !

## Dependencies
All the required dependencies are present in composer.json.
It is automatically installed by the Dockerfile.

## Deployment

The app is deployement ready.
It is currently deployed at `http://saberapp.herokuapp.com`.

