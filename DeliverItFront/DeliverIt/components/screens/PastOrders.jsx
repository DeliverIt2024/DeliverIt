import React, { useState, useEffect } from "react";
import { View, Text, StyleSheet, FlatList, ActivityIndicator, Alert, TouchableOpacity } from "react-native";
import axios from "axios";
import AsyncStorage from "@react-native-async-storage/async-storage";
import { format } from 'date-fns';

const PastOrdersScreen = ({ navigation }) => {
  const [orders, setOrders] = useState([]);
  const [items, setItems] = useState([]);
  const [loading, setLoading] = useState(true);

  const fetchPastOrders = async () => {
    try {
      const userId = await AsyncStorage.getItem("userId");
      if (!userId) {
        Alert.alert("Error", "User not logged in.");
        return;
      }

      const response = await axios.get(`http://localhost:8080/api/order/${userId}`);
      if (Array.isArray(response.data.data)) {
        setOrders(response.data.data);
      } else {
        Alert.alert("Error", "Failed to fetch past orders.");
      }
    } catch (error) {
      console.error(error);
      Alert.alert("Error", "Something went wrong.");
    } finally {
      setLoading(false);
    }
  };

  const fetchOrderItems = async (orderId) => {
    try {
      const response = await axios.get(`http://localhost:8080/api/order/items/${orderId}`);
      return response.data.items;
    } catch (error) {
      console.error("Failed to fetch order items:", error);
      return [];
    }
  };
  

  useEffect(() => {
    fetchPastOrders();
  }, []);

  if (loading) {
    return (
      <View style={styles.loaderContainer}>
        <ActivityIndicator size="large" color="#27AE60" />
      </View>
    );
  }

  if (!orders.length) {
    return (
      <View style={styles.container}>
        <Text style={styles.errorText}>No past orders found.</Text>
        <TouchableOpacity style={styles.button} onPress={() => navigation.goBack()}>
          <View style={styles.buttonTextWrapper}>
            <Text style={[styles.buttonText, styles.textStroke]}>Go Back</Text>
            <Text style={styles.buttonText}>Go Back</Text>
          </View>
        </TouchableOpacity>
      </View>
    );
  }

  return (
    <View style={styles.container}>
      <FlatList
        data={orders}
        keyExtractor={(item) => item.orderId.toString()}
        renderItem={({ item }) => (
            
          <View style={styles.orderCard}>
            <Text style={styles.orderText}>Order ID: {item.orderId}</Text>
            <Text style={styles.orderText}>Total: ${item.totalPrice}</Text>
            <Text style={styles.orderText}>
              Date: {format(new Date(item.orderDate.seconds * 1000), 'yyyy-MM-dd HH:mm:ss')}
            </Text>

        

            <TouchableOpacity
              style={styles.button}
              onPress={() => {/* Logic for reordering */}}
            >
              <View style={styles.buttonTextWrapper}>
                <Text style={[styles.buttonText, styles.textStroke]}>Order Again</Text>
                <Text style={styles.buttonText}>Order Again</Text>
              </View>
            </TouchableOpacity>
          </View>
        )}
      />
      <TouchableOpacity style={[styles.button, styles.cancelButton]} onPress={() => navigation.goBack()}>
        <View style={styles.buttonTextWrapper}>
          <Text style={[styles.buttonText, styles.textStroke]}>Cancel</Text>
          <Text style={styles.buttonText}>Cancel</Text>
        </View>
      </TouchableOpacity>
    </View>
  );
};

const styles = StyleSheet.create({
    container: {
      flex: 1,
      alignItems: "center",
      padding: 20,
      backgroundColor: "#f5f5f5",
    },
    loaderContainer: {
      flex: 1,
      justifyContent: "center",
      alignItems: "center",
      backgroundColor: "#f5f5f5",
    },
    orderCard: {
      backgroundColor: "#fff", 
      padding: 15,
      marginTop: 5,
      marginBottom: 5,  
      borderRadius: 12,  
      width: "100%",
      shadowColor: "#000",
      shadowOffset: { width: 0, height: 3 }, 
      shadowOpacity: 0.1,
      shadowRadius: 6,  
      elevation: 8,  
      borderWidth: 1,  
      borderColor: "#ddd",  
    },
    orderText: {
      fontSize: 18,
      color: "#000",
      marginBottom: 10,
    },
    button: {
      width: 250,
      height: 60,
      justifyContent: "center",
      alignItems: "center",
      borderRadius: 8,
      backgroundColor: "#27AE60",
      marginBottom: 20,
    },
    cancelButton: {
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
  
  

export default PastOrdersScreen;
