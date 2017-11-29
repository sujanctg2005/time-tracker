#install oracle driver
mvn install:install-file -DgroupId=oracle -DartifactId=ojdbc7 -Dpackaging=jar -Dversion=12.1.0.1 -Dfile=ojdbc7.jar -DgeneratePom=true