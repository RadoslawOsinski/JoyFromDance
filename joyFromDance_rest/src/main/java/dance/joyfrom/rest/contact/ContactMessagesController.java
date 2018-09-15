package dance.joyfrom.rest.contact;

import dance.joyfrom.services.contact.ContactMessage;
import dance.joyfrom.services.contact.ContactMessagesService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Radosław Osiński
 */
@RestController
public class ContactMessagesController {

    private final ContactMessagesService contactMessagesService;

    public ContactMessagesController(ContactMessagesService contactMessagesService) {
        this.contactMessagesService = contactMessagesService;
    }

    @RequestMapping(value = "/rest/contact/message", method = RequestMethod.POST,
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public void putMessage(@RequestBody ContactMessage contactMessage) {
        contactMessagesService.add(contactMessage);
    }

    @RequestMapping(value = "/authorized/rest/admin/contact/message", method = RequestMethod.GET,
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public List<ContactMessage> getMessages(@RequestParam Integer currentPage, @RequestParam Integer displayedRows,
                                            @RequestParam String name, @RequestParam String email
    ) {
        return contactMessagesService.get(currentPage, displayedRows, name, email);
    }

    @RequestMapping(value = "/authorized/rest/admin/contact/message/{messageId}", method = RequestMethod.DELETE,
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public void deleteMessages(@PathVariable Long messageId) {
        contactMessagesService.delete(messageId);
    }

}
