import React, { useState } from "react";
import { StyleSheet, Text, TextInput, Button, View, Alert } from "react-native";
import axios from "axios";
import AsyncStorage from "@react-native-async-storage/async-storage";

const LoginScreen = ({ navigation }) => {
  const [email, setEmail] = useState("test@email.com");
  const [password, setPassword] = useState("password");

  const handleLogin = async () => {
    try {
      const response = await axios.post("http://localhost:8080/api/user/login", { email, password });
      if (response.data.success) {
        const userId = response.data.data.userId; 
        console.log("Logged-in userId:", userId);

        await AsyncStorage.setItem("userId", userId);
        await AsyncStorage.setItem("isLoggedIn", "true");

        Alert.alert("Success", "Login successful!");
        navigation.navigate("Taskbar");
      } else {
        Alert.alert("Error", response.data.message || "Login failed!");
      }
    } catch (error) {
      console.error("Login Error:", error);
      Alert.alert("Error", "Something went wrong!");
    }
  };

  return (
    <View style={styles.container}>
      <Text style={styles.header}>Login</Text>
      <View style={styles.formContainer}>
        <TextInput
          style={styles.input}
          placeholder="Email"
          value={email}
          onChangeText={setEmail}
          keyboardType="email-address"
        />
        <TextInput
          style={styles.input}
          placeholder="Password"
          value={password}
          onChangeText={setPassword}
          secureTextEntry
        />
        <View style={styles.buttonContainer}>
          <View style={styles.button}>
            <Button title="Login" onPress={handleLogin} color="#27AE60" />
          </View>
        </View>
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center", // Center the contents horizontally
    paddingHorizontal: 20,
    backgroundColor: "#f5f5f5",
  },
  header: {
    fontSize: 24,
    fontWeight: "bold",
    marginBottom: 20,
    textAlign: "center",
    color: "#27AE60",
    textShadowColor: "#000",
    textShadowOffset: { width: -1, height: 1 },
    textShadowRadius: 1,
  },
  formContainer: {
    width: "80%", 
    alignItems: "center", 
  },
  input: {
    height: 40,
    width: 250,
    borderColor: "#27AE60",
    borderWidth: 1,
    marginBottom: 10,
    paddingHorizontal: 10,
    borderRadius: 5,
    backgroundColor: "#fff",
  },
  buttonContainer: {
    marginTop: 20,
    width: "100%",
    alignItems: "center",
  },
  button: {
    width: 250,
    height: 60,
    //justifyContent: "center",
    //alignItems: "center",
    //borderRadius: 8,
    //marginBottom: 20,
    textShadowColor: "#000",
    textShadowOffset: { width: -1, height: 1 },
    textShadowRadius: 1,
  },
});

export default LoginScreen;
