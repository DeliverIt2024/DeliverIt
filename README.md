# DeliverIt
# Table of Contents
1. [Overview](#overview)

2. [Product Spec](#product-spec)

3. [WireFrames](#wireframes)
  
4. [Schema](#schema)
# Overview

## Description
Allows users to incorperate location, preference, and budget in order to find an ideal meal for the day. Users can also interact with one another and share reccomended locations and restaurants based on what meals they have had while using DeliverIt.

## App Evaluation
- Category: Food and Bevarage / Delivery / Customer Service
- Story: Using information provided by the user, DeliverIt successfully finds restaurants in the location of user, reccomending them meals, and showing other users in the area that have 
  similiar preferences.
- Market: This app can be used by any person looking for a meal for whatever time of day.
- Habit: This app can be used whenever a user would look and will be avaliabe 24/7 based on the hours of operations of any location.
- Scope: Everyone has ran into a time where they cannot figure out for the life of them what they are going to eat. With DeliverIt, not only is the stress of finding a resturant 
  resolved, but the hassle of figuring out a specific meal for a certain place is eliminated as well. For any dinning establishment you choose, DeliverIt will provide the most pouplar 
  item on the menu and show you the meals and reviews of others that have ordered at that same establishment.
# Product Spec
## 1. User Stories (Required and Optional)
Comment
### Required Must-have Stories

- User logs in to access current avaliable restaurants, meals, and popular 
  reccomendations.
- User begins an order from a reccomendation or an avaliable restaurant.
- User is able to add other users as friends and share favorite meals and 
  restaurant locations with one another.
- Profile pages for each user.
- Settings (Accesibility, Notification, General, etc.)

### Optional Nice-to-have Stories

- Budget history for each order at each restaurant.
- Re-Order Button.
- Multiple payment method options (up to 8).
- Multiple saved locations for when traveling from one city to another.
- Food category reccomendations during the search process.

## 2. Screens
- Login
- Register - User signs up or logs into their account
  -- Upon Download/Reopening of the application, the user is prompted to log in to 
    gain access to their profile information to be properly recommended restaurants based on their information.
- Friends Screen - For users to communicate and connect with other users (direct 1-on-1)
    -- Allows users to add friends, chat with friends, and discover friends.
- Profile Screen
    -- Allows user to upload a photo and fill in information that can be used to connect them to others and recommend restaurants.
- Restaurant Recommendations Screen.
    -- Will serve as the home screen for the user where they are recommended new or old restaurants based on a number of factors.
- Order Screen
    -- The screen that the user experiences when they start and finish placing their order from the restaurant of their choosing.
- Search Screen
    -- A screen where users can manually search for restaurants.
- Map Screen
    -- A screen where the user can look for restaurants near their location or in a location of their choosing.
- Settings Screen
    -- Lets people customize accessibility, customize in-app experience, and app notification settings.

## 3. Navigation
### Tab Navigation (Tab to Screen)

- Home 
- Profile
- Settings
- Map
- Search

Optional:
- Discover (Top Choices of friends)

### Flow Navigation (Screen to Screen)

- Forced Log-in -> Account creation if no log in is available
- Home (optionally Discover top choices of friends) -> Gets taken to a page of recommendations for restaurants.
- Map -> shows the current general area of users with the restaurants in the area.
- Search -> takes user to search for restaurants manually.
- Profile -> Text field or photo to be modified. A second option is to see friends and interact with friends.
- Settings -> Toggle settings
# Wireframes
![IMG_7917](https://github.com/user-attachments/assets/63ea8686-82c6-415a-9f8f-7f745f4a5b11)

## Digital Wireframes & Mockups
![Untitled (4)](https://github.com/user-attachments/assets/9cf1e8b7-a4c9-4fb2-859b-a72c001c9006)

## Schema
### Models
*Users*
| Field Name      | Type    | Description                                          |
|-----------------|---------|------------------------------------------------------|
| `userId`        | String  | Unique identifier for the user.                      |
| `email`         | String  | User’s email address.                                |
| `name`          | String  | User's full name.                                    |
| `address`       | Object  | User’s shipping address (street, city, postal code). |
| `phoneNumber`   | String  | Contact number of the user.                          |
| `friends`       | Array   | List of users the user has friended                  |

*Restaurants*

| Field Name      | Type    | Description                                          |
|-----------------|---------|------------------------------------------------------|
| `restaurantId`  | String  | Unique identifier for the Restaurant.                |
| `email`         | String  | Restaurant’s email address.                          |
| `name`          | String  | Restaurant's full name.                              |
| `address`       |Geopoint | Restaurant’s address                                 |
| `phoneNumber`   | String  | Contact number of the Restaurant.                    |
| `menu`          | Array   |List of items on the menu to include prices, names, and descriptions.|
| `rating`        | Double  | a rating of a restauarant ranging from to 5.         |

*Menu items*

| Field Name      | Type    | Description                                          |
|-----------------|---------|------------------------------------------------------|
| `itemId`        | String  | Unique identifier for the menu item.                 |
| `price`         | Double  | price of the menu item.                              |
| `name`          | String  | Name of the menu item.                               |
| `Description`   | String  | Description of the menu item.                        |
| `Category`      | String  | Category the menu item belongs to in the menu.       |

*Users restaurant*

| Field Name      | Type    | Description                                          |
|-----------------|---------|------------------------------------------------------|
| `userId`        | String  | Reference to connect the table to a user.            |
| `restaurants`   | Array   |List of restaurants the user frequents or rated highly.|

*Delivery Driver*

| Field Name      | Type    | Description                                          |
|-----------------|---------|------------------------------------------------------|
| `ddId`        | String  | Unique identifier for the DD.                          |
| `email`         | String  | DD’s email address.                                  |
| `name`          | String  | DD's full name.                                      |
| `position`      |Geopoint | DD's current location if en route to a delivery.     |
| `phoneNumber`   | String  | Contact number of the DD.                            |
| `order`         | Object  | A reference to the order the DD is currently assigned to.|

*Orders*

| Field Name      | Type    | Description                                          |
|-----------------|---------|------------------------------------------------------|
| `orderId`       | String  | Unique identifier for the order.                     |
| `userId`        | String  | Reference to the user's Id that the order belong to. |
| `items`         | Array   | list of items in the order.                          |
| `address`       |Geopoint | Address of the user that the order is being sent to. |
| `phoneNumber`   | String  | phone number of the user the order is being sent to. |

### Networking
#### List of Network Request by Screen
- Home Screen
  -- (Read/GET) Query all orders where user is customer.
  
