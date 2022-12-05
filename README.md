# Welcome to Show Booking App

Show Booking App is a console application that takes input from command line to execute actions.

The application caters to 2 types of users
- Admin
- Buyer

## Admin

The users can setup and view the list of shows and seat allocation.

Commands (How to use)
- `setup <Show number> <Number of rows> <Number of seats per row> <Cancellation window in minutes>`
	- Setups the number of seats per show
	- e.g. `setup 1 2 2 1`
- `view <Show number>`
	- Displays the show number, and bookings for the show (ticket number, buyer phone number, seat number allocated to buyer
	- e.g. `view 1`
	
## Buyer

The users can retrieve list of available seats for a show, select 1 or more seats, buy and cancel tickets.

Commands (How to use)
- `availability <Show number>`
	- Displays all available seat numbers of specified show
	- e.g. `availability 1`
- `book <Show number> <Phone number> <Comma separated list of seats>`
	- Book tickets to a show. Will generate a unique ticket number
	- e.g. `book 1 91234566 A1,A2,A3`
- `cancel <ticket number> <phone number>`
	- Cancel tickets that were booked
	- e.g. `cancel 1 91234566`

## Misc

Misc commands (How to use)
- `commands`
	- Prints out all available commands and the parameters they accept
- `exit`
	- Exits the application

## Constraints
- Maximum of 10 seats per row
- Maximum of 26 rows
- Users can cancel their booking if time passed since booking is still within cancellation window of the show. After that cancellation is not allowed.
- ONLY 1 booking per phone number per show (Multiple bookings will be rejected)

## Assumptions
- Once shows are created, they cannot be overwritten by a new setup command

## Useful gradle commands to get started

### Build the project

Compiles the project, runs the test and then creates an executable JAR file

```console
$ ./gradlew build
```

### Run the application

Runs the compiled project and displays the welcome message of the console application

```console
$ ./gradlew run
```

## Requirements

The project requires [Java 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) or
higher.

The project makes use of Gradle and uses
the [Gradle wrapper](https://docs.gradle.org/current/userguide/gradle_wrapper.html), which means you don't need Gradle
installed.

## Some thoughts on improvements
- Implement page navigation to allows users to login/logout to switch between admin and buyers. This also allows handling of commands to be separated into their respective pages
	- The state design pattern can be considered for this implementation
- Implement classes for each command to encapsulate their logic and have better separation of concerns between the main console application and the handling of various commands
	- The command design pattern can be considered for this implementation
- Exception Handling when reading inputs should catch more specific exceptions instead of just one generic catch all exception
- More information feedback messages for users to understand why a command failed
- Make ShowManagementConsoleApp more testable
	- Possibly by attempting to give System.in through constructor injection and in junit test attempt to give my own input stream to the class to simulate user inputs
	
