Title: Selenium Grid FAQ
CSS: stylesheets/site.css stylesheets/document.css

<div class="header">
  <a href="index.html"><img alt="Selenium_grid_logo_large" src="images/selenium_grid_logo_large.png"/></a>
  <p>FAQ</p>
</div>

* This will become a table of contents (this text will be scraped).
{:toc}

Installation
============

General
=======

 Would you recommend using Selenium Grid for performance/Load testing?
 ---------------------------------------------------------------------

  Selenium Grid is not designed for performance and load testing, but very
  efficient web acceptance/functional testing. The main reason for this is
  that conducting performance/load testing with real browser is a pretty bad
  idea as it is hard/expensive to scale the load and the actual load is very
  inconsistent.

  For load/performance testing I would advise using tools like JMeter, Grinder
  or httperf. What you can do though, is reuse your selenium tests to record
  the use cases you will use for your load testing.

  To simulate 200 concurrent users for instance, you would need 200 concurrent
  browsers with a load testing framework based on Selenium Grid. Even if you
  use Firefox on Linux (so the most efficient setup) you will probably need at
  least 10 machines to generate that kind of load. Quite insane when
  JMeter/Grinder/httperf can generate the same kind of load with a single
  machine.

Launching the Hub and the Remote Controls
=========================================

 I need to run hub and remoute control in background.... How can I do it?
 ------------------------------------------------------------------------

  On UNIX you just add a ampersand at the en of the command line. See:

 * [Working with the UNIX shell](http://www.washington.edu/computing/unix/startdoc/shell.html)
 * [`nohup`](http://en.wikipedia.org/wiki/Nohup)
  
  On Windows you can use "start /wait/ /b" : [`start` command reference](http://www.ss64.com/nt/start.html)
  
  This said, if you are running on a UNIX platform or Mac OS X, the 
  easiest way to start the Hub and Remote Controls is to use the Rake 
  tasks that come with the default Selenium Grid distribution. 
  `cd` to the root of the Selenium distribution and launch:
  
      rake hub:start BACKGROUND=true
  
  to launch the hub in the background. Then
  
      rake rc:start BACKGROUND=true
  
  to launch a remote control in the background
  
  In practice, you can actually launch the hub and all the remote controls
  in the background with a single command:
  
      rake all:start
  
  and od course stop all of them in a similar way
  
      rake all:start
  
Configuring the Demo
====================

 How to setup the EC2 account for Amazon Web Services
 ----------------------------------------------------

 How to deploy the demo to the Amazon Web Services 
 -------------------------------------------------

 How to run the demo in the EC2 enviroment
 -----------------------------------------

 sdcs	

Running Examples
================

sdcds 

 How to run the examples in Java
 -------------------------------

 How to run the examples in Ruby 
 -------------------------------

 
 Is there a way to generate test reports using Selenium?
 -------------------------------------------------------

  The short answer is that yes you can generate test reports with Selenium.
  How to achieve this (and their exact format) will however depend on the
  programming language and test runner you are using (for instance 
  `JUnit`, `TestNG`, `Test::Unit` or `RSpec`).

  You can look at the [examples/ruby](http://svn.openqa.org/svn/selenium-grid/trunk/examples/ruby/) 
  directory in the Selenium Grid
  distribution to see how you can use RSpec and Selenium to generate reports
  which [include HTML capture and OS screenshots when a test
  fail](http://ph7spot.com/examples/rspec_report/index.html).

