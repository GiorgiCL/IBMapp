package com.giorgi.ibmapp.service;

import com.giorgi.ibmapp.domain.TicketCategory;
import com.giorgi.ibmapp.domain.TicketPriority;
import com.giorgi.ibmapp.integration.AiTicketDraft;
import org.springframework.stereotype.Component;

@Component
public class AiTicketDraftMapper {
    public TicketCategory resolveCategory(AiTicketDraft draft){
        try{
            return TicketCategory.valueOf(draft.getCategory().trim().toUpperCase());
        }catch(Exception exception){
            return TicketCategory.OTHER;
        }
    }
    public TicketPriority resolvePriority(AiTicketDraft draft){
        try{
            return TicketPriority.valueOf(draft.getPriority().trim().toUpperCase());
        }catch(Exception exception){
            return TicketPriority.MEDIUM;
        }
    }
    public String resolveTitle(AiTicketDraft draft){
        if(draft.getTitle() == null || draft.getTitle().isBlank()){
            return "AI generated support case";
        }
        return draft.getTitle().trim();
    }
    public String resolveSummary(AiTicketDraft draft,String originalCommentBody){
        if(draft.getSummary() == null || draft.getSummary().isBlank()){
            return originalCommentBody;
        }
        return draft.getSummary().trim();
    }
}
