import React, { useState, useEffect } from "react";
import { StyleSheet, Text, View, FlatList, ActivityIndicator } from "react-native";
import axios from "axios";

const Option4Screen = () => {
  const [vendors, setVendors] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchVendors = async () => {
      try {
        const response = await axios.get("http://localhost:8080/api/vendor/");
        console.log("Vendors data:", response.data.data); // Log the response data for debugging
        setVendors(response.data.data); // Set the vendors data
      } catch (error) {
        console.error("Failed to fetch vendors:", error);
      } finally {
        setLoading(false);
      }
    };

    fetchVendors();
  }, []);

  if (loading) {
    return (
      <View style={styles.loaderContainer}>
        <ActivityIndicator size="large" color="#27AE60" />
      </View>
    );
  }

  // If vendors data is empty, show a message
  if (vendors.length === 0) {
    return (
      <View style={styles.container}>
        <Text>No vendors available.</Text>
      </View>
    );
  }

  return (
    <View style={styles.container}>
      <Text style={styles.header}>Vendors</Text>
      <FlatList
        data={vendors}
        keyExtractor={(item) => item.vendorId ? item.vendorId.toString() : item.id.toString()}
        renderItem={({ item }) => {
          console.log("Item data:", item); // Log item data for debugging
          return (
            <View style={styles.vendorCard}>
              <Text style={styles.vendorName}>{item.name || "Name not available"}</Text>
              <Text>{item.email || "Email not available"}</Text>
            </View>
          );
        }}
        
      />
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: "center",
    paddingHorizontal: 20,
  },
  loaderContainer: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
    backgroundColor: "#f5f5f5",
  },
  header: {
    fontSize: 24,
    fontWeight: "bold",
    marginBottom: 20,
    alignItems: "center",
  },
  vendorCard: {
    backgroundColor: "#fff",
    padding: 15,
    marginBottom: 10,
    borderRadius: 12,
    shadowColor: "#000",
    shadowOffset: { width: 0, height: 3 },
    shadowOpacity: 0.1,
    shadowRadius: 6,
    elevation: 8,
  },
  vendorName: {
    fontSize: 18,
    fontWeight: "bold",
  },
});

export default Option4Screen;
