-- AppleScript to launch 10 remote controls starting on port 5000
--
-- launch it with `osascript launch-remote-controls.applescript`
set the_port to 5000
repeat until (the_port = 5010)
   log "Lauching remote control on port " & the_port

		tell application "Terminal"
		  activate
		  do script with command "cd ~/work/selenium-grid-1.0; ./launch-rc.sh " & the_port & " ; exit"
      tell window 1
				set title displays shell path to true
				set title displays file name to true
				set title displays custom title to true
				set custom title to "Selenium Remote Control on port " & the_port
				set number of columns to 80
        set number of rows to 20
      end
		end tell

   set the_port to the_port + 1
end repeat





