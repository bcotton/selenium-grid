Title: Selenium Grid FAQ
CSS: stylesheets/site.css stylesheets/document.css stylesheets/faq.css

<div class="header">
  <a href="index.html"><img alt="Selenium_grid_logo_large" src="images/selenium_grid_logo_large.png"/></a>
  <p>FAQ</p>
</div>

Table Of Content:

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

 How can I pass additional parameters to the Remote Controls when starting them as part of Selenium Grid?
 --------------------------------------------------------------------------------------------------------

  Additional parameters can be passed to Selenium remote controls at startup

  Just set the `seleniumArgs` Java property when launching the remote control. 
  For instance, to start a remote control in multi window and debug mode you 
  would use:

    ant -DseleniumArgs="-multiWindow -debug" launch-remote-control

  Of course you can also achieve the same thing with the Rake task:

    rake hub:start SELENIUM_ARGS="-multiWindow -debug"


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

 When starting Firefox I get: java java.lang.RuntimeException: Firefox refused shutdown while preparing a profile"
 ------------------------------------------------------------------------------------------------------------------

> Here is my log on this error:
> ...
> java Caused by: org.openqa.selenium.server.browserlaunchers.FirefoxChromeLauncher$FileLockRemai nedException: Lock file still present! C:\DOKUME1\Semadou\LOKALE1\Temp\customProfileDir9d4a3879bb7d4ca5b75dbbb488ec30b1\parent.lock


  Sometimes Selenium Remote Control does not stop Firefox properly on Windows
  and things get very messy (leaving lock files behind). This does happen when
  you Ctrl-C while running the test suite for instance.

  If you encounter this problem, I would advise you to:

* Kill all running Firefox instances and make sure that there is no Firefox process in the task manager (or even better reboot)
* Delete all the directories: `C:\DOCUME1\<your login>\LOCALS1\Temp\customProfileDir*`
* While you are at it cleanup `C:\DOCUME1\<your login>\LOCALS1\Temp as much as possible`
* Run your tests or the demo again

 Why do I have duplicate entries in the grid hub after restarting my RCs?
 ------------------------------------------------------------------------

> Say I have a hub setup with three RCs ready and waiting, and then I
> go and restart those RCs, my hub now shows 6 RCs eventhough there are
> actually only 3. Is this anything to worry about? Will the hub try
> to send requests to the "dead" entries? Do I have to restart the hub
> everytime I need to restart and RC?

  You are probably stopping the remote controls a little too "harshly"? For
  instance, if you do a `kill -9` or use Windows task manager, the JVM shutdown
  hook does not have a chance to unregister the remote control and the Hub
  does not realize that the remote control is gone. 
  
  But if you stop the remote control in a more "civil" manner everything should
  be fine (e.g. `kill` or using the shutdown method in selenium client api). 
  Even better use the Rake tasks provided with Selenium Grid distribution:
  `rake rc:start_all` and `rake rc:stop_all` multiple times on my machine. 
  
  If you end up in this state I do recommend that you restart the Hub which
  would otherwise end up in a "non predictable state". Future releases of
  Selenium Grid will take care of this problem transparently, but for now a
  restart is safer.

 Can I configure the Remote Control to use a custom HTTP proxy?
 --------------------------------------------------------------

> I am having problems with the http proxy settings when I launch the
> Remote Controls with Selenium Grid ant task: I tried
>
> ant -Dport=5556 -Dhttp.proxyHost=my_proxy.my_company.com -Dhttp.proxyPort=3128 launch-remote-control
>
> which works with a standard remote control, but not with Selenium Grid launcher.

  The problem is that `ant launch-remote-control` does launch the remote
  control as a forked Java process (which does not inherit the system
  properties that you are passing to Ant). You can edit the
  build.xml file at the root of your Selenium Grid distribution and add the
  system properties you need. For instance:

    <target name="launch-remote-control" description="Launch A Remote Control">
      <java classpathref="remote-control.classpath"
            classname="com.thoughtworks.selenium.grid.remotecontrol.SelfRegisteringRemoteControlLauncher"
            fork="true"
            failonerror="true">
  
        <sysproperty key="http.proxyHost" value="${http.proxyHost}"/>
        <sysproperty key="http.proxyPort" value="${http.proxyPort}"/>
  
        <arg value="-port"/>
        <arg value="${port}"/>
        <arg value="-host"/>
        <arg value="${host}"/>
        <arg value="-hubURL"/>
        <arg value="${hubURL}"/>
        <arg value="-env"/>
        <arg value="${environment}"/>
        <arg line="${seleniumArgs}"/>      
      </java>
    </target>

  Alternatively, you can also just 
  [build your own Selenium Grid distribution from source](http://selenium-grid.openqa.org/build_it_from_source.html)
  as the fix in already checked-in in the codebase.

Configuring the Demo
====================

 How to setup the EC2 account for Amazon Web Services
 ----------------------------------------------------

 How to deploy the demo to the Amazon Web Services 
 -------------------------------------------------

 How to run the demo in the EC2 enviroment
 -----------------------------------------


Running the Examples Included in Selenium Grid Distribution
===========================================================

 How to run the Java example
 ---------------------------

1. Go to the root directory of your Selenium Grid distribution

2. Launch Selenium Grid Hub and 4 remote controls as explained in
   ["Run the Demo"](http://selenium-grid.openqa.org/run_the_demo.html)

3. Go to the Java example directory: `cd ./examples/java`

4. Launch the tests with: `ant run`  

 How to Run the Ruby Example
 ---------------------------

1. Go to the root directory of your Selenium Grid distribution

2. (Re)start Selenium Grid Hub and the remote controls with:
   `rake all:restart`

3. Go to the Ruby example directory: `cd ./examples/ruby`

4. Launch the tests with: `rake tests:run_in_parallel`  


 HTTPS
 -----

>  We have a client where application is built on HTTPS. We tried testing using selenium but its not supporting.
>
>  Can you please suggest us the approach to be followed to test HTTPS URL’s.

  http://blog.thirstybear.co.uk/2008/05/selenium-and-https.html

However, in some cases, you may be required to test more than one domain at
once. The most common case is when you need to test both http://blah.com and
https://blah.com. "http:" and "https:" are considered different "origins" from
JavaScript perspective, so tests running on one can't run on the other. In
that case, for now, you'll have to use one of our experimental browser
launchers which support running in multiple domains. (Also be sure to read the
HTTPS section of this documentation for more details about HTTPS support.)


Running Your Tests Against Selenium Grid
========================================

I have some test cases and I want to run them against Selenium Grid, what do I need to do?
------------------------------------------------------------------------------------------

  The idea is that all you have to do to take advantage of the Selenium Grid
  is to point your Selenium client driver to the Hub and run your tests in
  parallel.

### Java ###
  
  If you writing your tests using Java, the best is to run your
  tests with [TestNG parallel runner](http://testng.org/doc/documentation-main.html#parallel-running). 

  You can find a concrete example on
  how this can be achieved in the standard Selenium Grid distribution under
  the [`examples/java`](http://svn.openqa.org/svn/selenium-grid/trunk/examples/java/) 
  directory.

### Ruby ###

  If you use Ruby, the best is to use
  [DeepTest](http://deep-test.rubyforge.org) which can even distribute the test run 
  accross multiple machines.

  You can find a concrete example (a nice test reports) on
  how this can be achieved in the standard Selenium Grid distribution under
  the [`examples/ruby`](http://svn.openqa.org/svn/selenium-grid/trunk/examples/ruby/) 
  directory.

### Python ###

  I have no experience in Python so I do not know what the best solution is.
  Nevertheless, I can give you the theory and some starting points.
  
   Basically you need to come up with a way to run your python tests in
  parallel. How exactly you achieve this usually depends on your testing
  framework and programming language of choice. I am not personally aware of
  any parallel test runner for Python but a little Googling found the
  following starting points that you could investigate:
  
 * [`py.test` distributed testing section](https://codespeak.net/py/dist/test.html#automated-distributed-testing)
 * [Parallel Python](http://www.parallelpython.com/)
 * [Testoob](http://testoob.sourceforge.net/features.html)
 * [Mailing list archive on parallel testing in Python](http://lists.idyll.org/pipermail/testing-in-python/2007-December/thread.html#463)

  If none of this is helpful, worst case scenario, you can also write your own
  parallel test runner by launching multiple processes targeting different
  test file sets and checking the process exit statuses. Not the most
  elegant/efficient way, but that can get you started. This is actually the
  way I originally started with Ruby and you can find an example on how this
  worked in the Ruby example included in Selenium Grid distribution:

  [`examples/ruby/lib/multi_process_behaviour_runner.rb`](http://svn.openqa.org/svn/selenium-grid/trunk/examples/ruby/lib/multi_process_behaviour_runner.rb)

  You launch the whole thing with:

    #
    # Legacy way to drive tests in parallel before DeepTest RSpec support.
    # Kept to document a simple way to run the tests in parallel for non-Ruby
    # platforms.
    #
    desc("[DEPRECATED] Run all behaviors in parallel spawing multiple
    processes. DeepTest offers a better alternative.")
    task :'tests:run_in_parallel:multiprocess' => :create_report_dir do
     require File.expand_path(File.dirname(__FILE__) +
    '/lib/multi_process_behaviour_runner')
     runner = MultiProcessSpecRunner.new(10)
     runner.run(Dir['*_spec.rb'])
    end

  Good luck in your quest for the ultimate Python parallel test runner. Please
  [contact me](http://ph7spot.com/about/contact_me) if you figure out the best
  solution for Python, I will put it in the documentation. Even better,
  send me an example, I will include it in Selenium Grid distribution.

### Other ###

  To take advantage of Selenium Grid power, you need to come up with a way to
  run your tests in parallel. How exactly you achieve this usually depends on
  your testing framework and programming language of choice. Try
  googling around for a parallel or distributed test runner for your language.

  If you cannot find any, your fallback plan is to write your own parallel
  test runner by launching multiple processes targeting different test file
  sets and checking the process exit statuses. Not the most elegant/efficient
  way, but that can get you started. This is actually the way I originally
  started with Ruby and you can find an example on how this worked in the Ruby
  example included in Selenium Grid distribution:

  [`examples/ruby/lib/multi_process_behaviour_runner.rb`](http://svn.openqa.org/svn/selenium-grid/trunk/examples/ruby/lib/multi_process_behaviour_runner.rb)

  You launch the whole thing with:

    #
    # Legacy way to drive tests in parallel before DeepTest RSpec support.
    # Kept to document a simple way to run the tests in parallel for non-Ruby
    # platforms.
    #
    desc("[DEPRECATED] Run all behaviors in parallel spawing multiple
    processes. DeepTest offers a better alternative.")
    task :'tests:run_in_parallel:multiprocess' => :create_report_dir do
     require File.expand_path(File.dirname(__FILE__) +
    '/lib/multi_process_behaviour_runner')
     runner = MultiProcessSpecRunner.new(10)
     runner.run(Dir['*_spec.rb'])
    end

  Good luck in your quest for the ultimate parallel test runner for your
  favorite language. Please [contact me](http://ph7spot.com/about/contact_me) if you
  figure out the best solution for your language, I will put it in the documentation.
  Even better, send me an example, I will include it in Selenium Grid
  distribution.

 Is there a way to generate test reports using Selenium?
 -------------------------------------------------------

  The short answer is that yes you can generate test reports with Selenium.
  How to achieve this (and their exact format) will however depend on the
  programming language and test runner you are using (for instance 
  `JUnit`, `TestNG`, `Test::Unit` or `RSpec`).

  You can look at the [`examples/ruby`](http://svn.openqa.org/svn/selenium-grid/trunk/examples/ruby/) 
  directory in the Selenium Grid
  distribution to see how you can use RSpec and Selenium to generate reports
  which [include HTML capture and OS screenshots when a test
  fail](http://ph7spot.com/examples/rspec_report/index.html).

My test cases are in HTML (Selense), how can I run those against Selenium Grid ?
------------------------------------------------------------------

You would need a parallel test runner for Selenium Grid.

I might eventually end up working on such a parallel test runner for HTML 
test suites, nevertheless my time is limited and this feature is quite low 
in my priority list: in my experience HTML test suites are a nightmare 
to maintain you are better off writing and refactoring real code by the time 
your test suite grows big enough that it takes too long to run.

This said, there might be hope as some guys seem to be working on it though: see 
[this thread](http://clearspace.openqa.org/thread/11482)


Analysing Failures
==================

 When we test the applicaiton with Selenium Grid, we get nondeterministic results
 --------------------------------------------------------------------------------

> Locally, when we test the application with Selenium Grid, we get 
> nondeterministic results. Tests seem to fail randomly. Messing with the 
> number of nodes in the grid seems to help, but its really annoying that we 
> can't seem to get consistent results.

  Most likely some tests are timing out in a non-deterministic manner because
  your CPU or Network is over-utilized. Monitor your CPU and Network activity on
  all the machines involved. Once you find the bottleneck launch fewer
  processes. For instance if your load average is way higher than the number of
  CPUs on the machine running the remote controls, cut the number of remote
  controls you launch by two until you get to a sustainable machine load.

  Make sure you spend some time figuring out the optimal number of 
  concurrent test runners and remote controls to run in parallel on each 
  machine, before deploying a Selenium Grid infrastructure your organization
  is going to depend on.

Development
===========

 Where Can I Get Feedback Selenium Grid on Continuous Integration Builds?
 ------------------------------------------------------------------------
  
  Check out the Selenium Grid builds on 
  [http://teamcity.openqa.org](http://teamcity.openqa.org) (you can login as a guest user).


