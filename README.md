
Pre-work - ToDo App

ToDoApp is an android app that allows building a todo list and basic todo items management functionality including adding new items, editing and deleting an existing item.

Submitted by: Liubov Sireneva

Time spent: 20 hours spent in total

## User Stories

The following **required** functionality is completed:

* [x] User can **successfully add and remove items** from the todo list
* [x] User can **tap a todo item in the list and bring up an edit screen for the todo item** and then have any changes to the text reflected in the todo list.
* [x] User can **persist todo items** and retrieve them properly on app restart

The following **optional** features are implemented:

* [x ] Persist the todo items into SQLite instead of a text file
* [x ] Improve style of the todo items in the list using a custom adapter
* [x ] Add support for completion due dates for todo items 
* [x ] Use a DialogFragment and Fragments instead of new Activity for editing items
* [x ] Add support for selecting the priority of each todo item (and display in listview item)
* [x ] Tweak the style improving the UI / UX, play with colors, images or backgrounds
* [x]  Use a Toolbar for navigating between Fragments and MainActivity


The following **additional** features are implemented:

* [x] User can't add an empty item

## Video Walkthrough

Here's a walkthrough of implemented user stories:

<img src='http://imgur.com/a/HEyxK' title='ToDoApp'/>

GIF created with Vokoscreen.

## Project Analysis

As part of your pre-work submission, please reflect on the app and answer the following questions below:

"What are your reactions to the Android app development platform so far? Compare and contrast Android's approach to layouts and user interfaces in past platforms you've used."

I used to develop Android app 4 years ago, and now I would like to refresh knoweledge and learn new features since that time.
I like this platform, because it is well documented and has a friendly development environment.

"Take a moment to reflect on the `ArrayAdapter` used in your pre-work. How would you describe an adapter in this context and what is its function in Android? Why do you think the adapter is important? Explain the purpose of the `convertView` in the `getView` method of the `ArrayAdapter`."

The adapter associates an array of data with a set of TextView elements. When we are working with lists, we are dealing with three components. First, it's the list items (ListView, GridView) that display the data. 
Secondly, it is a data source - an array, an ArrayList object, a database, etc., in which  data itself is located. And thirdly, it's adapters are special components that connect the data source to the list item. So adapter is very important component.
ConvertView is used to reuse old View objects, it's needed for save memory.For example if you have a list with a lot of items, but screen can show only 10 items, then at first convertView would be null, 
and we need to create new views for these 10 items, but when you scroll down, you have two options, to create 10 views, or re-use old Views and load new data into these views.

## License

    Copyright 2017 Liubov Sireneva

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.