import React, { useEffect, useState } from "react";
import { View, Text, StyleSheet, FlatList, ActivityIndicator } from "react-native";

const ChatScreen = ({ route }) => {
  const { chatId } = route.params;
  const [messages, setMessages] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [userIdOne, setUserIdOne] = useState(null);
  const [userIdTwo, setUserIdTwo] = useState(null);

  useEffect(() => {
    const fetchMessages = async () => {
      try {
        const response = await fetch(`http://localhost:8080/api/chat/chat/${chatId}`);
        if (!response.ok) {
          throw new Error(`HTTP Error: ${response.status}`);
        }
        const data = await response.json();

        if (data.success && data.data) {
          setMessages(data.data.messages);
          setUserIdOne(data.data.userIdOne);
          setUserIdTwo(data.data.userIdTwo);
        } else {
          setError("Failed to load messages.");
        }
      } catch (err) {
        setError(err.message || "An error occurred.");
      } finally {
        setLoading(false);
      }
    };

    fetchMessages();
  }, [chatId]);

  const formatTimestamp = (timestamp) => {
    const date = new Date(timestamp);
    return `${date.toLocaleDateString()} ${date.toLocaleTimeString()}`;
  };

  if (loading) {
    return (
      <View style={styles.loaderContainer}>
        <ActivityIndicator size="large" color="#27AE60" />
        <Text>Loading messages...</Text>
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

  return (
    <View style={styles.container}>
      <View style={styles.headerContainer}>
        <View style={styles.textStrokeContainer}>
          <Text style={[styles.header, styles.textStroke]}>Chat</Text>
          <Text style={styles.header}>Chat</Text>
        </View>
      </View>
      <FlatList
        data={messages}
        keyExtractor={(item, index) => index.toString()}
        renderItem={({ item }) => (
          <View
            style={[
              styles.messageContainer,
              item.userId === userIdTwo
                ? styles.messageLeft
                : styles.messageRight,
            ]}
          >
            <Text style={styles.messageText}>{item.text}</Text>
            <Text style={styles.timestamp}>{formatTimestamp(item.timestamp)}</Text>
          </View>
        )}
      />
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: "#82E0AA",
  },
  loaderContainer: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
    backgroundColor: "#82E0AA",
  },
  headerContainer: {
    backgroundColor: "#27AE60",
    paddingVertical: 15,
    alignItems: "center",
    marginBottom: 10,
  },
  header: {
    fontSize: 24,
    fontWeight: "bold",
    color: "#F7DC6F",
    textAlign: "center",
    width: "100%",
  },
  textStrokeContainer: {
    position: "relative",
    alignItems: "center",
  },
  textStroke: {
    position: "absolute",
    color: "transparent",
    textShadowColor: "#000",
    textShadowOffset: { width: -1, height: 1 },
    textShadowRadius: 1,
  },
  errorText: {
    color: "red",
    fontSize: 16,
    textAlign: "center",
  },
  messageContainer: {
    maxWidth: "70%",
    marginVertical: 5,
    padding: 10,
    borderRadius: 12,
  },
  messageLeft: {
    alignSelf: "flex-start",
    backgroundColor: "#E8F8F5",
  },
  messageRight: {
    alignSelf: "flex-end",
    backgroundColor: "#D4EFDF",
  },
  messageText: {
    fontSize: 16,
    color: "#333",
  },
  timestamp: {
    fontSize: 12,
    color: "#888",
    marginTop: 5,
    textAlign: "right",
  },
});

export default ChatScreen;
