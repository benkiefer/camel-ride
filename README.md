##Camel Ride

**What is this?**

An example of a route that will read a csv file, pick up the file move it to another directory and process each line of the file as a csv record. The entries of the csv are mapped to the Person and kept in a list so they can be asserted in a test.

Check out the FileMover class for more information.

The app module is a complete WAR file that can be run from the command line using the `maven:jetty run' command.

**Build Notes**

A 'PROJECT_OUTPUT_PATH' environment variable is required for this build. For example, 'c:/temp' will work just fine.