package ChatUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatIO {
    private static List<MessageLine> conversation = new ArrayList<>();
    private static List<String> conversationString = new ArrayList<>();

    public static List<MessageLine> readChat(File f) throws FileNotFoundException {

//        Clear the conversation
        conversation.clear();

//        Clear the conversationString
        conversationString.clear();

        Scanner sc = new Scanner(f);
        String line = null;

//        Read line by line
        while (sc.hasNextLine()) {
            line = sc.nextLine();
            line = line.replace("\u200e", "").replace("\r", "").trim();

            conversationString.add(line);
        }


        forceLineWithDatesAtTheFront(conversationString);
        parseMessages();

        return getConversation();
    }

    private static void forceLineWithDatesAtTheFront(List<String> c) {
        int lastLineNonNull = 0;
        int lineWithoutDateCount = 0;

        for (int i = 0; i < c.size(); i++) {
            String line = c.get(i);

            if (!line.startsWith("[")) {
                lineWithoutDateCount++;
                lastLineNonNull = (i - lineWithoutDateCount);
                StringBuilder nc = new StringBuilder(c.get(lastLineNonNull));
                nc.append(line);

//             Set the item to the ArrayList
                c.set(lastLineNonNull, nc.toString());
                c.set(i, null);
            } else {
                lineWithoutDateCount = 0;
                lastLineNonNull = 0;
            }
        }

        List<String> temp = new ArrayList<>();

//        Check if it is null or not, if not add it to the temp list
        for (String cv : conversationString) {
            if (cv != null) {
                temp.add(cv);
            }
        }

        conversationString = temp;
    }

    private static void parseMessages() {
        Pattern p = Pattern.compile("\\[(\\d+)/(\\d+)/(\\d+), (\\d+):(\\d+):(\\d+) ([APM]+)\\] ([^:]+): (.+)", Pattern.CASE_INSENSITIVE);

        for (String line : conversationString) {
            Matcher m = p.matcher(line);

            if (m.find()) {
                int day = Integer.parseInt(m.group(1));
                int month = Integer.parseInt(m.group(2));
                int year = Integer.parseInt(m.group(3));
                int hour = Integer.parseInt(m.group(4));
                int mins = Integer.parseInt(m.group(5));
                int sec = Integer.parseInt(m.group(6));
                String ampm = m.group(7);
                String author = m.group(8);
                String content = m.group(9);

                if (ampm.equals("PM")) {
                    hour += 12;
                }

                conversation.add(
                        new MessageLine(day, month, year, hour, mins, sec, author, content)
                );
            }
        }
    }

    public int getNumberOfLines() {
        return conversation.size();
    }

    public static List<MessageLine> getConversation() {
        return conversation;
    }
}
