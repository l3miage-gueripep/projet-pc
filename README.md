# Projet PC

## Installation
Pour installer le projet, installer maven, puis executer cette commande.

Windows :
```
mvn clean install:install-file -Dfile="lib/shape-1.0.1.jar" -DgroupId="edu.uga.miage.m1" -DartifactId="edu.uga.singleshape" -Dversion="1.0.1" -Dpackaging=jar -DgeneratePom=true

mvn clean install:install-file -Dfile="lib/edu.uga.json-1.0.0.jar" -DgroupId="fr.uga.miage.m1" -DartifactId="edu.uga.json" -Dversion="1.0.0" -Dpackaging=jar -DgeneratePom=true
```

Linux :
```
mvn clean install:install-file -Dfile=lib/shape-1.0.1.jar -DgroupId=edu.uga.miage.m1 -DartifactId=edu.uga.singleshape -Dversion=1.0.1 -Dpackaging=jar -DgeneratePom=true
```