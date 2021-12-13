# Simple-Reminders-App <img src="https://i.imgur.com/z4jOIlv.png" width="15%" align="right">

## Overview
Create a reminder with three simple steps:
1. Give it a Name
2. Give it a Prority Level
3. Give it a Time

The application will send a notification when the appropriate time comes. 

This project was created as the final project for the course CSC 221 Software Design.

## Features
### Creating a Reminder  <img src="https://i.imgur.com/Ooxccyj.gif" width="15%" align="right"> <img src="https://i.imgur.com/QXzO2GC.gif" width="15%" align="right"> <img src="https://i.imgur.com/dzoEy9y.gif" width="15%" align="right">

- Create a new reminder with button located on the bottom right
- Give the reminder a name from the input view
- Select a priority [Low], [Medium], [High]
- Schedule a time for the reminder using one of the two following options:
  - Quickset Options
  - Advance Options
- Once everything is filled, click the confirm button to create the reminder
- If the input view is empty, the name of the reminder will be called “Untitled Reminder”
- If the time scheduled is in the past, it will display a error dialog

### Deleting a Reminder <img src="https://user-images.githubusercontent.com/70610982/145739900-f796d1f4-f072-4df4-868b-a15e43f6a857.gif" width="15%" align="right"> <img src="https://i.imgur.com/lB3TBxW.gif" width="15%" align="right">

- Swipe RIGHT on a reminder to remove it from the ArrayList and the database
- A Toast message will display, confirming the process has been completed successfully

### Reminders are stored in a SQLite Database
- On startup, all previous reminders stored in the database will be
  displayed to the home screen

  
- This ensure that all previous reminders that you have created is
  not lost once the application restarts.

### Notifications
Placeholder

<br>

## Demonstration Video
[![Demo Video](https://img.youtube.com/vi/4eiiM8bUsOw/0.jpg)](https://www.youtube.com/watch?v=4eiiM8bUsOw)

