import React from "react";
import { createBottomTabNavigator } from "@react-navigation/bottom-tabs";
import Icon from "react-native-vector-icons/MaterialIcons";
import Option1Screen from "./UserTabs/Chats";
import Option2Screen from "./UserTabs/Home";
import Option3Screen from "./UserTabs/Map";
import Option4Screen from "./UserTabs/Profile";
import Option5Screen from "./UserTabs/Search";

const Tab = createBottomTabNavigator();

const TaskbarNavigator = () => (
  <Tab.Navigator
    screenOptions={({ route }) => ({
      tabBarIcon: ({ focused }) => {
        let iconName;
        switch (route.name) {
          case "Map":
            iconName = "map";
            break;
          case "Chats":
            iconName = "notifications";
            break;
          case "Home":
            iconName = "home";
            break;
          case "Profile":
            iconName = "account-circle";
            break;
          default:
            iconName = "help";
        }

        const iconColor = focused ? "#F7DC6F" : "#000";
        return <Icon name={iconName} size={25} color={iconColor} />;
      },
      tabBarStyle: {
        backgroundColor: "#27AE60", 
        borderTopWidth: 0,
        height: 60,
        paddingBottom: 5,
        paddingTop: 5,
        elevation: 10, // Shadow for Android
        shadowColor: "#000", // Shadow for ios
        shadowOffset: { width: 0, height: 2 },
        shadowOpacity: 0.2,
        shadowRadius: 4,
      },
      tabBarLabelStyle: {
        fontSize: 12,
        color: "#F7DC6F", 
        fontWeight: "bold",
      },
      tabBarActiveTintColor: "#F7DC6F", 
      tabBarInactiveTintColor: "#fff", 
      headerShown: false, 
    })}
  >
      <Tab.Screen name="Home" component={Option5Screen} />
    <Tab.Screen name="Chats" component={Option1Screen} />
    <Tab.Screen name="Profile" component={Option4Screen} />
  </Tab.Navigator>
);

export default TaskbarNavigator;
