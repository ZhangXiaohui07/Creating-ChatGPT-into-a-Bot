import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendAudio;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Bot extends TelegramLongPollingBot {
    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new Bot());
        } catch (TelegramApiException e) {
            System.out.println("Error initializing the bot: " + e.getMessage());
        }
    }

    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        if (message != null && message.hasText()) {
            if (message.getText().equals("/help")) {
                sendMessage(message, "This is a help message.");
            } else if (message.getText().equals("/time")) {
                sendMessage(message, "The current time is " + new Date().toString());
            } else {
                sendMessage(message, "You sent: " + message.getText());
            }
        }
    }

  else if (message.getText().equals("/audio")) {
                sendAudio(message, "path/to/audio.mp3");
            } else if (message.getText().equals("/image")) {
                sendPhoto(message, "path/to/image.jpg");
            } else if (message.getText().equals("/document")) {
                sendDocument(message, "path/to/document.pdf");
            }
  
  SendMessage sendMessage = new SendMessage();
sendMessage.setChatId(-GROUP_ID); // set chat_id to the group's id
sendMessage.setText("Hello, I'm ChatGPT! How can I help you today?");
try {
    execute(sendMessage);
} catch (TelegramApiException e) {
    e.printStackTrace();
}
  
    private void sendMessage(Message message, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(text);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            System.out.println("Error sending message: " + e.getMessage());
        }
    }
  
    private void sendAudio(Message message, String audio) {
        SendAudio sendAudio = new SendAudio();
        sendAudio.setChatId(message.getChatId().toString());
        sendAudio.setReplyToMessageId(message.getMessageId());
        sendAudio.setAudio(new File(audio));
        try {
            execute(sendAudio);
        } catch (TelegramApiException e) {
            System.out.println("Error sending audio: " + e.getMessage());
        }
    }

    private void sendPhoto(Message message, String photo) {
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(message.getChatId().toString());
        sendPhoto.setReplyToMessageId(message.getMessageId());
        sendPhoto.setPhoto(new File(photo));
        try {
            execute(sendPhoto);
        } catch (TelegramApiException e) {
            System.out.println("Error sending photo: " + e.getMessage());
        }
    }

    private void sendDocument(Message message, String document) {
        SendDocument sendDocument = new SendDocument();
        sendDocument.setChatId(message.getChatId().toString());
        sendDocument.setReplyToMessageId(message.getMessageId());
        sendDocument.setDocument(new File(document));
        try {
            execute(sendDocument);
        } catch (TelegramApiException e) {
            System.out.println("Error sending document: " + e.getMessage());
        }
    }
    
    public String getBotUsername() {
        return "YourBotName";
    }

    public String getBotToken() {
        return "YourBotToken";
    }
}
