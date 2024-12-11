import React, { useState } from "react";
import { StyleSheet, Text, TextInput, TouchableOpacity, View, Alert } from "react-native";
import axios from "axios";

const Register = ({ navigation }) => {
  const [formData, setFormData] = useState({
    firstName: "",
    lastName: "",
    username: "",
    email: "",
    password: "",
    phone: "",
  });

  const handleChange = (name, value) => {
    setFormData({ ...formData, [name]: value });
  };

  const handleSubmit = async () => {
    const { firstName, lastName, username, email, password, phone } = formData;

    if (!firstName || !lastName || !username || !email || !password || !phone) {
      Alert.alert("Error", "All fields are required!");
      return;
    }

    try {
      const response = await axios.post("http://localhost:8080/api/user/add", formData);
      if (response.data.success) {
        Alert.alert("Success", "User account created successfully!");
        navigation.navigate("Login");
      } else {
        Alert.alert("Error", response.data.message || "An error occurred!");
      }
    } catch (error) {
      Alert.alert("Error", error.response?.data?.message || "Failed to register!");
    }
  };

  return (
    <View style={styles.container}>
      <View style={styles.textWrapper}>
        <Text style={[styles.headerText, styles.textStroke]}>Create an Account</Text>
        <Text style={styles.headerText}>Create an Account</Text>
      </View>

      <View style={styles.formContainer}>
        <TextInput
          style={styles.input}
          placeholder="First Name"
          value={formData.firstName}
          onChangeText={(text) => handleChange("firstName", text)}
        />
        <TextInput
          style={styles.input}
          placeholder="Last Name"
          value={formData.lastName}
          onChangeText={(text) => handleChange("lastName", text)}
        />
        <TextInput
          style={styles.input}
          placeholder="Username"
          value={formData.username}
          onChangeText={(text) => handleChange("username", text)}
        />
        <TextInput
          style={styles.input}
          placeholder="Email"
          value={formData.email}
          onChangeText={(text) => handleChange("email", text)}
          keyboardType="email-address"
        />
        <TextInput
          style={styles.input}
          placeholder="Phone"
          value={formData.phone}
          onChangeText={(text) => handleChange("phone", text)}
          keyboardType="phone-pad"
        />
        <TextInput
          style={styles.input}
          placeholder="Password"
          value={formData.password}
          onChangeText={(text) => handleChange("password", text)}
          secureTextEntry
        />

        <TouchableOpacity style={[styles.button, styles.registerButton]} onPress={handleSubmit}>
          <Text style={styles.buttonText}>Register</Text>
        </TouchableOpacity>

        <TouchableOpacity
          style={[styles.button, styles.loginButton]}
          onPress={() => navigation.navigate("Login")}
        >
          <Text style={styles.buttonText}>Login</Text>
        </TouchableOpacity>
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
    paddingHorizontal: 20,
    backgroundColor: "#fff", // Same background as WelcomeScreen
  },
  textWrapper: {
    alignItems: "center",
    marginBottom: 40,
  },
  headerText: {
    fontSize: 30,
    fontWeight: "bold",
    color: "#F7DC6F",
    textAlign: "center",
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
  formContainer: {
    width: "100%",
    alignItems: "center",
  },
  input: {
    height: 50,
    width: 250,
    borderColor: "#27AE60",
    borderWidth: 1,
    borderRadius: 8,
    marginBottom: 12,
    paddingLeft: 10,
    fontSize: 18,
    color: "#333",
    backgroundColor: "f5f5f5",
  },
  button: {
    width: 250,
    height: 60,
    justifyContent: "center",
    alignItems: "center",
    borderRadius: 8,
    marginBottom: 20,
  },
  registerButton: {
    backgroundColor: "#27AE60",
  },
  loginButton: {
    backgroundColor: "#F7DC6F",
  },
  buttonText: {
    fontSize: 20,
    fontWeight: "bold",
    color: "#fff",
    textShadowColor: "#000",
    textShadowOffset: { width: -1, height: 1 },
    textShadowRadius: 1,
  },
});

export default Register;
