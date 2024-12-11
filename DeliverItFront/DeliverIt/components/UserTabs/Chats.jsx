import React, { useEffect, useState } from "react";
import { View, Text, StyleSheet, ActivityIndicator, FlatList } from "react-native";
import AsyncStorage from "@react-native-async-storage/async-storage";

const Option2Screen = () => {
  const [userId, setUserId] = useState(null);
  const [conversations, setConversations] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  // Fetch userId from AsyncStorage
  useEffect(() => {
    const fetchUserId = async () => {
      try {
        const storedUserId = await AsyncStorage.getItem("userId");
        if (storedUserId) {
          setUserId(storedUserId);
        } else {
          setError("User not logged in. Please log in.");
        }
      } catch (err) {
        console.error("Error fetching userId:", err);
        setError("Failed to fetch user information.");
      }
    };
    fetchUserId();
  }, []);

  // Fetch chats using the userId
  useEffect(() => {
    if (!userId) return;

    const fetchChats = async () => {
      try {
        const response = await fetch(`http://localhost:8080/api/chat/${userId}`);
        if (!response.ok) {
          throw new Error(`HTTP Error: ${response.status}`);
        }

        const data = await response.json();
        if (data.success && data.data) {
          setConversations(data.data);
        } else {
          setError("Failed to load chats.");
        }
      } catch (err) {
        console.error("Error fetching chats:", err);
        setError(err.message || "An error occurred.");
      } finally {
        setLoading(false);
      }
    };

    fetchChats();
  }, [userId]);

  if (loading) {
    return (
        <View style={styles.container}>
          <ActivityIndicator size="large" color="#27AE60" />
          <Text>Loading chats...</Text>
        </View>
    );
  }

  if (error) {
    return (
        <View style={styles.container}>
          <Text style={styles.errorText}>Error: {error}</Text>
        </View>
    );
  }

  if (conversations.length === 0) {
    return (
        <View style={styles.container}>
          <Text>No conversations found.</Text>
        </View>
    );
  }

  const groupMessagesByUser = (messages) => {
    const groupedMessages = [];
    let currentUserId = null;
    let currentMessages = [];

    messages.forEach((message) => {
      if (message.userId !== currentUserId) {
        if (currentMessages.length > 0) {
          groupedMessages.push({ userId: currentUserId, messages: currentMessages });
        }
        currentUserId = message.userId;
        currentMessages = [];
      }
      currentMessages.push(message);
    });

    if (currentMessages.length > 0) {
      groupedMessages.push({ userId: currentUserId, messages: currentMessages });
    }

    return groupedMessages;
  };

  return (
      <View style={styles.container}>
        <FlatList
            data={conversations}
            keyExtractor={(item) => item.chatId}
            renderItem={({ item }) => (
                <View style={styles.chatContainer}>
                  <Text style={styles.chatHeader}>
                    Chat with: {item.userIdOne === userId ? item.userIdTwo : item.userIdOne}
                  </Text>
                  {groupMessagesByUser(item.messages).map((group, index) => (
                      <View key={index} style={styles.messageGroup}>
                        <Text style={styles.messageGroupHeader}>
                          {group.userId === userId ? "You" : group.userId}
                        </Text>
                        {group.messages.map((message, msgIndex) => (
                            <View
                                key={msgIndex}
                                style={[
                                  styles.messageContainer,
                                  message.userId === userId ? styles.messageRight : styles.messageLeft,
                                ]}
                            >
                              <Text style={styles.messageText}>
                                {message.text || "No text found!"} {/* Accessing the correct property */}
                              </Text>
                            </View>
                        ))}
                      </View>
                  ))}
                </View>
            )}
        />
      </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: 10,
    backgroundColor: "#f5f5f5",
  },
  errorText: {
    color: "red",
    fontSize: 16,
    textAlign: "center",
  },
  chatContainer: {
    marginBottom: 20,
    backgroundColor: "#fff",
    borderRadius: 8,
    padding: 10,
    shadowColor: "#000",
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 0.1,
    shadowRadius: 4,
    elevation: 2,
  },
  chatHeader: {
    fontSize: 18,
    fontWeight: "bold",
    marginBottom: 10,
  },
  messageGroup: {
    marginBottom: 15,
  },
  messageGroupHeader: {
    fontSize: 16,
    fontWeight: "bold",
    marginBottom: 5,
  },
  messageContainer: {
    marginVertical: 5,
    padding: 10,
    borderRadius: 8,
    maxWidth: "70%",
    backgroundColor: "#f0f0f0", // Set a neutral background color
  },
  messageRight: {
    alignSelf: "flex-end",
    backgroundColor: "#27AE60", // Green background for user messages
  },
  messageLeft: {
    alignSelf: "flex-start",
    backgroundColor: "#d3d3d3", // Grey background for other user messages
  },
  messageText: {
    color: "#000", // Set text color to black for better visibility
  },
});

export default Option2Screen;
