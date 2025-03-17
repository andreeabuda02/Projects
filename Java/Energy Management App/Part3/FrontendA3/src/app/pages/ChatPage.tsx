// ChatPage.tsx
import React, { useEffect, useRef, useState } from "react";

import SockJS from "sockjs-client";
import { Client } from '@stomp/stompjs';
import { useNavigate, useLocation } from "react-router-dom";
import axios from "axios";

import { USERS_HOST, WEBSOCKET_MESSAGE_HOST, BROKER_MESSAGE_HOST, MESSAGING_HOST } from "./hosts";
import { backButtonStyle, buttonContainerStyle, chatContainerStyle, chatLogStyle, chatModalStyle, closeButtonStyle, contactItemStyle, contactListStyle, messageInputSectionStyle, messageInputStyle, messageItemStyle, messageLeftStyle, messageRightStyle, messageStatusStyle, messageTextStyle, newMessageStyle, parentDivStyle, searchButtonStyle, searchSectionStyle, sendButtonStyle, typingIndicatorStyle } from "../styles/ChatPage.style";
import { string } from "prop-types";


export const ChatPage = (): JSX.Element => {

  const [isHovered, setIsHovered] = useState(false);
  const hoverStyle = isHovered ? { backgroundColor: '#e3f2fd', transform: 'scale(1.02)' } : {};

  const endpoint = { user: "user" };

  const endpointmsg1 = { message: "interactions/" };
  const endpointmsg2 = { message: "conv/" };
  const endpointmsg3 = { message: "read/" };
  const endpointmsg4 = { message: "msg/" };
  const endpointmsg5 = { message: "unread/" };

  const endpointWebsocketMessageSend = { message: "/frontend/send" };
  const endpointWebsocketWriting = { message: "/topic/writing" };
  const websocket = { message: "/topic/read" };
  const endpointWebsocketMessageReceive = { message: "/topic/newMessage" };

  // PENTRU MESAJE
  const [chatUserList, setChatUserList] = useState<any[]>([]);
  const [recentChatUsers, setRecentChatUsers] = useState<any[]>([]);
  const [activeChatUser, setActiveChatUser] = useState<any | null>(null);
  const [isLoadingMessages, setIsLoadingMessages] = useState<boolean>(false);
  const [sendChatMessageFunction, setSendChatMessageFunction] = useState<((senderId: string, receiverId: string, message: string) => void) | null>(null);
  const [readChatMessageFunction, setReadChatMessageFunction] = useState<((senderId: string, receiverId: string) => void) | null>(null);
  const [notifyTypingFunction, setNotifyTypingFunction] = useState<((senderId: string, receiverId: string, isTyping: boolean) => void) | null>(null);
  const [pendingMessages, setPendingMessages] = useState<any[]>([]);
  const [usersWithPendingMessages, setUsersWithPendingMessages] = useState<any[]>([]);
  const [hasUnreadChats, setHasUnreadChats] = useState<boolean>(false);
  const [isTyping, setIsTyping] = useState<boolean>(false);
  const activeChatUserRef = useRef(activeChatUser);

  const [emailValue, setEmailValue] = useState<string>("");
  const [contactList, setContactList] = useState<any[]>([]);
  const [chatLog, setChatLog] = useState<any[]>([]);
  const [isLoading, setIsLoading] = useState<boolean>(false);
  const [draftText, setDraftText] = useState<string>("");
  const [recentMessageSenders, setRecentMessageSenders] = useState<any[]>([]);

  const [hoveredItem, setHoveredItem] = useState<string | null>(null);

  const navigate = useNavigate();
  const location = useLocation();

  const socketMessaging = new SockJS(WEBSOCKET_MESSAGE_HOST.backend_api);
  const stompUserMessaging = new Client({
    brokerURL: BROKER_MESSAGE_HOST.backend_api,
    connectHeaders: {},
    reconnectDelay: 5000,
    webSocketFactory: () => socketMessaging,
    debug: (msg) => console.log(msg)
  });

  const onContactSelect = (user: any) => {
    if (!user || !user.id_u) {
      console.error("Invalid user object or id_u is undefined:", user);
      return;
    }
    setActiveChatUser(user);
    viewUserChatHistory(user);
    const idForUser = user.id_u;
    if(readChatMessageFunction){
      readChatMessageFunction(String(idForUser), location.state.userId)
    }
  };

  const handleBack = () => {
    if (location.state.userRole === "admin") {
      navigate("/adminpage", {
        state: {
          email: location.state.email,
          token: location.state.token,
          userId: location.state.userId
        }
      });
    }
    else {
      if (location.state.userRole === "client") {
        navigate("/clientpage", {
          state: {
            email: location.state.email,
            token: location.state.token,
            userId: location.state.userId
          }
        });
      }
    }
  };

  ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////MESAJE FUNCTII

  // Recupereaza istoricul conversatiei dintre utilizatorul activ si un contact selectat
  const fetchMessageHistory = async (recipientId: string, senderId: string): Promise<any[]> => {
    try {
      const response = await axios.get(
        `${MESSAGING_HOST.backend_api}${endpointmsg2.message}${recipientId}/${senderId}`
      );
      return response.status === 200 ? response.data : [];
    } catch (error) {
      console.error('Error retrieving chat history:', error);
      return [];
    }
  };

  //  Recupereaza mesajele necitite pentru utilizatorul conectat(apeleaza endpoint-ul din MessageController care interogheaza
  //  mesajele necitite pentru un anumit utilizator pe baza ID-ului acestuia)
  const getUnreadMessages = async (userId: string): Promise<void> => {
    try {
      const response = await axios.get(
        `${MESSAGING_HOST.backend_api}${endpointmsg5.message}${userId}`
      );
      if (response.status === 200) {
        setPendingMessages(response.data);
      } else {
        setPendingMessages([]);
      }
    } catch (error) {
      setPendingMessages([]);
    }
  };

  //Identifica utilizatorii care au trimis mesaje recent
  const identifyChatParticipants = (newChats: any[]) => {
    const activeUsers = chatUserList.filter((user) =>
      newChats.some((chat) => chat.senderId === user.id_u)
    );
    setUsersWithPendingMessages(activeUsers);
  };

  //Dupa ce s-a citit mesajul il elimina din lista de mesaje necitite
  const removeSeenMessages = (recipientId: string, senderId: string) => {
    const updatedChats = pendingMessages.filter(
      (message) => !(message.receiverId === recipientId && message.senderId === senderId)
    );
    setPendingMessages(updatedChats);
    identifyChatParticipants(updatedChats);
  };

  //  Marcheaza mesajele ca fiind citite in backend dupa afisarea lor
  const confirmMessagesAsRead = async (recipientId: string, senderId: string): Promise<void> => {
    try {
      const response = await axios.get(
        `${MESSAGING_HOST.backend_api}${endpointmsg3.message}${recipientId}/${senderId}`
      );
      if (response.status === 200) {
        console.log('Messages successfully marked as read.');
      } else {
        console.log('Failed to mark messages as read.');
      }
      removeSeenMessages(recipientId, senderId);
    } catch (error) {
      //aci
      console.error('Error confirming message read status:', error);
    }
  };

  // Recupereaza lista utilizatorilor disponibili pentru chat.
  const retrieveChatUsers = async (): Promise<void> => {
    try {
      const response = await axios.get(
        `${USERS_HOST.backend_api}${endpoint.user}`,
        { headers: { 'Authorization': `Bearer ${location.state.token}` } }
      );
      if (response.status === 200) {
        console.log(response.data); // Verifică structura datelor primite
        setChatUserList(response.data);
        findChatParticipants(response.data);
      } else {
        setChatUserList([]);
        findChatParticipants([]);
        alert('Unable to load chat users.');
      }
    } catch (error) {
      setChatUserList([]);
      findChatParticipants([]);
      alert('An error occurred while loading chat users.');
    }
  };

  // Configureaza conexiunea WebSocket pentru comunicarea in timp real si interactioneaza cu endpoint-urile WebSocket definite in WebsocketConfig.java
  const initializeUserMessagingSocket = (userId: string): void => {
    stompUserMessaging.onConnect = (frame) => {
      console.log('Connected to the user WebSocket successfully:', frame);

      stompUserMessaging.subscribe(websocket.message, async (msg) => {
        const bodyMessage = JSON.parse(msg.body);
        if (userId === bodyMessage.receiverId && activeChatUserRef.current?.id_u === bodyMessage.senderId) {
          await confirmMessagesAsRead(bodyMessage.receiverId, bodyMessage.senderId);
        }
      });

      setReadChatMessageFunction(() => (senderId: string, receiverId: string) => {
        stompUserMessaging.publish({
          destination: websocket.message,
          body: JSON.stringify({
            senderId: senderId,
            receiverId: receiverId
          })
        });
      });

      stompUserMessaging.subscribe(endpointWebsocketMessageReceive.message, async (msg) => {
        const newMessage = JSON.parse(msg.body);
        const recipientId = newMessage.receiverId;
        const senderId = newMessage.senderId;
        const activeChatUserId = activeChatUserRef.current?.id_u ?? "-1";

        if ((userId === recipientId && activeChatUserId === senderId) ||
          (activeChatUserId === recipientId && userId === senderId)) {
          setIsLoadingMessages(true);
          if (recipientId === userId) {
            await confirmMessagesAsRead(recipientId, senderId);
            if (readChatMessageFunction) {
              readChatMessageFunction(activeChatUserId, userId);
            }
          }
          const messages = await fetchMessageHistory(recipientId, senderId);
          setChatLog(messages);
          await retrieveChatUsers();
          setIsLoadingMessages(false);
        } else {
          setPendingMessages((prev) => [...prev, newMessage]);
          setHasUnreadChats(true);
        }
      });

      stompUserMessaging.subscribe(endpointWebsocketWriting.message, (msg) => {
        const bodyMessage = JSON.parse(msg.body);
        if (userId === bodyMessage.receiverId && activeChatUserRef.current?.id_u === bodyMessage.senderId) {
          setIsTyping(bodyMessage.writing);
        }
      });

      setSendChatMessageFunction(() => (senderId: string, receiverId: string, messageDetails: string) => {
        const timestamp = Date.now();
        stompUserMessaging.publish({
          destination: endpointWebsocketMessageSend.message,
          body: JSON.stringify({
            senderId: senderId,
            receiverId: receiverId,
            messageDetails: messageDetails,
            timestamp: timestamp,
            messageRead: false
          })
        });
      });

      setNotifyTypingFunction(() => (senderId: string, receiverId: string, typingStatus: boolean) => {
        stompUserMessaging.publish({
          destination: endpointWebsocketWriting.message,
          body: JSON.stringify({
            senderId: senderId,
            receiverId: receiverId,
            writing: typingStatus
          })
        });
      });
    };

    stompUserMessaging.onStompError = (frame) => {
      console.error('User WebSocket Error:', frame.headers['message']);
      console.error('Details:', frame.body);
    };
  };

  const findUserByEmail = () => {
    const matchedUser = chatUserList.find((user) => user.email === emailValue);

    if (matchedUser) {
      setActiveChatUser(matchedUser);
      console.log(matchedUser);
      viewUserChatHistory(matchedUser);
      setEmailValue("");
      if(readChatMessageFunction){
        readChatMessageFunction(matchedUser.id_u, location.state.userId);
      }
    } else {
      alert(`No user found with the email: "${emailValue}".`);
    }
  };

  const fetchChatHistoryForUser = async (): Promise<void> => {
    try {
      const response = await axios.get(
        `${MESSAGING_HOST.backend_api}${endpointmsg1.message}${location.state.userId}`
      );

      if (response.status === 200) {
        const userIds = response.data;
        const matchedUsers = chatUserList.filter(user => userIds.includes(user.id_u));
        setRecentChatUsers(matchedUsers);
      } else {
        setRecentChatUsers([]);
      }
    } catch (error) {
      console.error('Failed to retrieve user chat history:', error);
      setRecentChatUsers([]);
    }
  };

  //Recupereaza istoricul conversatiei dintre utilizatorul activ si un contact selectat, marcand mesajele ca fiind citite
  const viewUserChatHistory = async (selectedUser: any): Promise<void> => {
    setIsLoadingMessages(true);

    try {
      await confirmMessagesAsRead(location.state.userId, selectedUser.id_u);
      const messages = await fetchMessageHistory(selectedUser.id_u, location.state.userId);
      setChatLog(messages);
    } catch (error) {
      console.error('Error loading chat history:', error);
    } finally {
      setIsLoadingMessages(false);
    }
  };

  const returnToChatUserList = (): void => {
    setActiveChatUser(null);
    fetchChatHistoryForUser();
    setChatLog([]);
    identifyChatParticipants(pendingMessages);
    setIsTyping(false);
    setDraftText("");
  };


  const onEmailChange = (enteredEmailText: string) => {
    setEmailValue(enteredEmailText);
  }

  //Actualizeaza textul mesajului aflat in redactare si notifica backend-ul despre starea de scriere
  //Trimite notificari prin WebSocket la /topic/writing, indicand daca utilizatorul scrie sau nu
  const onDraftTextChange = (draftText: string) => {
    if (notifyTypingFunction) {
      if (draftText === "") {
        notifyTypingFunction(location.state.userId, activeChatUser.id_u, false);
      }
      else {
        notifyTypingFunction(location.state.userId, activeChatUser.id_u, true);
      }
    }
    setDraftText(draftText);
  }

  // Trimite un mesaj catre utilizatorul selectat.
  // Publica mesajul la /frontend/send prin WebSocket.
  // Backend-ul proceseaza mesajul, il salveaza in baza de date (MessageRepository.save) si il transmite destinatarului prin /topic/newMessage
  const sendMessageAction = () => {
    if (draftText === "") {
      return;
    }
    if (sendChatMessageFunction) {
      sendChatMessageFunction(location.state.userId, activeChatUser.id_u, draftText);
      setDraftText("");
      if (notifyTypingFunction) {
        notifyTypingFunction(location.state.userId, activeChatUser.id_u, false);
      }
    }
    else {
      console.log("Sending error!");
    }
  }

  const findChatParticipants = (receivedMessages: any[]) => {
    const users = chatUserList.filter(user =>
      receivedMessages.some(message => user.userId === message.senderId)
    );
    setContactList(users);
  };

  /////////////////////////////////////////////////////////////////////////////////USE UFFECTS

  useEffect(() => {
    activeChatUserRef.current = activeChatUser;
  }, [activeChatUser]);

  useEffect(() => {
    fetchChatHistoryForUser();
  }, [chatUserList]);

  useEffect(() => {
    if (!location.state || !location.state.email || !location.state.token) {
      navigate("/authentication");
      return;
    }

    retrieveChatUsers();
    getUnreadMessages(location.state.userId);

    initializeUserMessagingSocket(location.state.userId);

    if (!stompUserMessaging.active) {
      stompUserMessaging.activate();
    }
  }, []);

  /////////////////////////////////////////////////////////////////////////////////HTML

  return (
    <div style={parentDivStyle}>
      <div style={buttonContainerStyle}>
        {/* Afiseaza butonul Back doar cand nu este completata cautarea */}
        <button style={backButtonStyle} onClick={handleBack}>Back</button>
      </div>
      <div style={chatModalStyle}>
        {activeChatUser ?
          ( //////////////////////////////////////////////////////////////////////////////conversatie modal

            <div style={chatContainerStyle}>
              <button style={closeButtonStyle} onClick={() => { returnToChatUserList(); }}>Close</button>

              <h3>
                Chat with {activeChatUser.name}{" "}
                {activeChatUser.role === "admin" ? "(Admin)" : ""}{" "}
                {activeChatUser.id_u === location.state.userId ? "(You)" : ""}
              </h3>
              {/* Chat Messages Section */}
              <div style={chatLogStyle}>
                {chatLog.map((message) => (
                  <div
                    key={message.messageId}
                    style={{
                      ...messageItemStyle,
                      ...(message.senderId === location.state.userId
                        ? messageRightStyle
                        : messageLeftStyle)
                    }}
                  >
                    <p style={messageTextStyle}>{message.messageDetails}</p>
                    {message.senderId === location.state.userId && (
                      <span style={messageStatusStyle}>
                        {message.messageRead ? "✓✓" : "✓"}
                      </span>
                    )}
                  </div>
                ))}
              </div>

              {/* Typing Indicator */}
              {isTyping && (
                <p style={typingIndicatorStyle}>{activeChatUser.name} is typing...</p>
              )}

              {/* Message Input Section */}
              <div style={messageInputSectionStyle}>
                <input
                  type="text"
                  value={draftText}
                  onChange={(e) => onDraftTextChange(e.target.value)}
                  placeholder="Type a message..."
                  style={messageInputStyle} />
                <button style={sendButtonStyle} onClick={sendMessageAction}>
                  Send
                </button>
              </div>
            </div>
          ) : (
            ///////////////////////////////////////////////////////////////////////////////////////////////// principal conversatie
            <>
              {/* Loading Spinner */}
              {isLoading ? (
                <p>Loading...</p>
              ) : (
                <>

                  {/* Search Section */}
                  {location.state.userRole === "admin" ? (
                    <div style={searchSectionStyle}>
                      <select value={emailValue} onChange={(e) => onEmailChange(e.target.value)}>
                        <option value="">Select a user</option>
                        {chatUserList.map((user) => (
                          <option key={user.id_u} value={user.email}>
                            {user.email}
                          </option>
                        ))}
                      </select>

                      <button style={searchButtonStyle} onClick={findUserByEmail}>
                        Search
                      </button>
                    </div>
                  ) : (
                    <div style={searchSectionStyle}>
                      <input
                        type="text"
                        value={emailValue}
                        onChange={(e) => onEmailChange(e.target.value)}
                        placeholder="Enter a user's email"
                      />
                      <button style={searchButtonStyle} onClick={findUserByEmail}>
                        Search
                      </button>
                    </div>
                  )}

                  {/* Contact List Section */}
                  <div style={contactListStyle}>
                    {recentChatUsers.length > 0 ? (
                      recentChatUsers.map((user) => (
                        <div
                          key={user.id_u}
                          onClick={() => onContactSelect(user)}
                          onMouseEnter={() => setHoveredItem(user.id_u)}
                          onMouseLeave={() => setHoveredItem(null)}
                          style={hoveredItem === user.id_u ? { ...contactItemStyle, ...hoverStyle } : contactItemStyle}
                        >

                          <p style={recentMessageSenders.includes(user) ? newMessageStyle : {}}>
                            {user.name} {user.role === "admin" ? "(Admin)" : ""}
                          </p>
                          <p>{user.email}</p>
                        </div>
                      ))
                    ) : (
                      <p>No conversations yet. Start a new chat!</p>
                    )}
                  </div>

                </>
              )}
            </>
          )}

      </div>
    </div >
  );

};
export default ChatPage;