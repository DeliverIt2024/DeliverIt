import React, { useState } from "react";
import { View, Text, StyleSheet, ActivityIndicator, Alert, Image, TouchableOpacity } from "react-native";
import axios from "axios";
import AsyncStorage from "@react-native-async-storage/async-storage";
import { useFocusEffect } from "@react-navigation/native";

const ProfileScreen = ({ navigation }) => {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);

  const fetchProfile = async () => {
    try {
      setLoading(true); // Show loading spinner during fetch
      const userId = await AsyncStorage.getItem("userId");
      if (!userId) {
        Alert.alert("Error", "User not logged in.");
        return;
      }

      const response = await axios.get(`http://localhost:8080/api/user/${userId}`);
      if (response.data.success) {
        setUser(response.data.data);
      } else {
        Alert.alert("Error", "Failed to fetch user profile.");
      }
    } catch (error) {
      console.error(error);
      Alert.alert("Error", "Something went wrong.");
    } finally {
      setLoading(false); 
    }
  };

  useFocusEffect(
    React.useCallback(() => {
      fetchProfile(); 
    }, [])
  );

  const handleSignOut = async () => {
    try {
      await AsyncStorage.clear();
      Alert.alert("Success", "You have been signed out.");
      navigation.replace("Login");
    } catch (error) {
      console.error("Error during sign-out:", error);
      Alert.alert("Error", "Failed to sign out.");
    }
  };

  if (loading) {
    return (
      <View style={styles.loaderContainer}>
        <ActivityIndicator size="large" color="#27AE60" />
      </View>
    );
  }

  if (!user) {
    return (
      <View style={styles.container}>
        <Text style={styles.errorText}>User data could not be loaded.</Text>
      </View>
    );
  }

  return (
    <View style={styles.container}>
      <Image
        source={{ uri: user.profilePhotoUrl || "https://via.placeholder.com/150" }}
        style={styles.profilePicture}
      />
      <Text style={styles.name}>{user.firstName} {user.lastName}</Text>
      <View style={styles.buttonContainer}>
        <TouchableOpacity
          style={styles.button}
          onPress={() => navigation.navigate("EditProfile", { user })}
        >
          <View style={styles.buttonTextWrapper}>
            <Text style={[styles.buttonText, styles.textStroke]}>Edit Profile</Text>
            <Text style={styles.buttonText}>Edit Profile</Text>
          </View>
        </TouchableOpacity>
        <TouchableOpacity style={styles.button} onPress={() => navigation.navigate("Settings")}>
          <View style={styles.buttonTextWrapper}>
            <Text style={[styles.buttonText, styles.textStroke]}>Settings</Text>
            <Text style={styles.buttonText}>Settings</Text>
          </View>
        </TouchableOpacity>
        <TouchableOpacity style={styles.button} onPress={() => navigation.navigate("PastOrders")}>
          <View style={styles.buttonTextWrapper}>
            <Text style={[styles.buttonText, styles.textStroke]}>Past Orders</Text>
            <Text style={styles.buttonText}>Past Orders</Text>
          </View>
        </TouchableOpacity>
        <TouchableOpacity style={[styles.button, styles.signOutButton]} onPress={handleSignOut}>
          <View style={styles.buttonTextWrapper}>
            <Text style={[styles.buttonText, styles.textStroke]}>Sign Out</Text>
            <Text style={styles.buttonText}>Sign Out</Text>
          </View>
        </TouchableOpacity>
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: "center",
    padding: 20,
    backgroundColor: "#82E0AA",
  },
  loaderContainer: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
    backgroundColor: "#f5f5f5",
  },
  profilePicture: {
    width: 150,
    height: 150,
    borderRadius: 75,
    marginBottom: 20,
    borderWidth: 2,
    borderColor: "#27AE60",
  },
  name: {
    fontSize: 32,
    fontWeight: "bold",
    color: "#000",
    marginBottom: 30,
  },
  buttonContainer: {
    width: "100%",
    alignItems: "center",
  },
  button: {
    width: 250,
    height: 60,
    justifyContent: "center",
    alignItems: "center",
    borderRadius: 8,
    backgroundColor: "#27AE60",
    marginBottom: 15,
  },
  signOutButton: {
    backgroundColor: "#F7DC6F",
  },
  buttonTextWrapper: {
    position: "relative",
  },
  buttonText: {
    fontSize: 30,
    fontWeight: "bold",
    color: "#fff",
  },
  textStroke: {
    position: "absolute",
    left: 0,
    right: 0,
    color: "black",
    textShadowColor: "black",
    textShadowOffset: { width: -1, height: 1 },
    textShadowRadius: 1,
  },
  errorText: {
    color: "red",
    fontSize: 16,
  },
});

export default ProfileScreen;
