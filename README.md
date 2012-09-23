##Camel Ride

**What is this?**

This project is a bunch of reference points that I put together while working through Camel in Action. Hopefully they help you too.

The project is a Spring-based Web App so that it mirrors something you might actually create and use in real life.

##About the Examples##
Most of the examples can be tweaked to include a different destination simply by changing the destination value in the property file. For example, you could change FileMover to be a FileToQueueMover by changing the destination to a queue.

**FileMover**
 - Simplest example of camel routing. Picks up a file and dumps it in another directory.

**ContentBasedFileMover:**
 - Determines the type of file based on extension and routes accordingly

**FileToQueueMover:**
 - Picks up the content of a file and dumps it on a queue.

**LoggingProcessor:**
 - A utility class that you can hook in at just about any point and get information about what is going on.

**MultipleDestinationDeliverer:**
 - Send the same file to multiple locations using a multicast.

**WireTapMover:**
 - Send the same file to multiple locations using a wire tap.

**XstreamTransformingMover:**
 - Take a csv input file, convert each row into an object, then write each object to a separate xml file using xstream.

**JaxbTransformingMover:**
 - Take a csv input file, convert each row into an object, then write each object to a separate xml file using JAXB.

##A Few Words of Caution:##
Camel doesn't do much validation. If you screw up your destination or incorrectly write your Simple expression language, it's easy to get stuck in an infinite loop.

If you're having trouble, inject the LoggingProcessor into various stages of your route and use it to figure out what is going on.

Good luck.
