# Helpingo - HACK36 Project
The idea of this project is taken from a very serious management problem.
Due to delay in information retrieval of the mishap and proper action that will be taken by the management, a large amount of resources and people accidents happens.
What we propose is a common people based surevellance system where people only will notify the management about the mishaps of any kind, such as fire, accidents, health problems, management problems etc.
We have been working on this idea because peoples are one which will always be present around any mishap, so the information retrieval will be instant and proper action can be taken against it.

A good question: why people will take such effort to notify the management?
well we have thought of it too!! what we do is give up rewards to such people helping in notifying a genuine mishap.

Tech stack:

1. Android
2. PHP (REST APIs)
3. MySQL
4. JAVA (for system software)
5. Raspberry Pi (For collaborating on our own server)


This Application uses REST APIs developed in php to connect/fetch/insert data to mySql DB. Both the REST APIs and mySQL DB (phpmyadmin panel) is accessed through raspberry pie server.

REST APIs developed are stored at: https://github.com/amalkumar308/helpingo_php

A stand alone JAVA Application is also being developed to access the notifications sent through android application in realtime.

JAVA Application is stored at: https://github.com/lokesh525/helpingo
