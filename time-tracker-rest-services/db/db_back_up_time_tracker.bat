echo 'start backup'

set hour=%time:~0,2%
if "%time:~0,1%"==" " set hour=0%time:~1,1%
set _datetime=%date:~10,4%-%date:~4,2%-%date:~7,2%_%hour%%time:~3,2%

exp userid=time_tracker/tracker  file=C:\database\backup\time_tracker_%_datetime%.dmp
pause