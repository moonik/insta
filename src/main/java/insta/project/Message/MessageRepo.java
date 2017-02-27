package insta.project.Message;

import java.util.List;

public interface MessageRepo {

    List<Message> findMyConverstations(String currentUser);
}
