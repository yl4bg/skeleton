package controllers;

import api.CreateReceiptRequest;
import api.ReceiptResponse;
import dao.ReceiptDao;
import dao.TagDao;
import generated.tables.records.ReceiptsRecord;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Path("/tags/{tag}")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TagController {
    final TagDao tags;
    final ReceiptDao receipts;

    public TagController(TagDao tags, ReceiptDao receipts) {
        this.tags = tags;
        this.receipts= receipts;
    }

    @PUT
    public int toggleTag(@PathParam("tag") String tagName, @NotNull String id) {

        int number=Integer.parseInt(id);
        return tags.toggle(tagName, Integer.parseInt(id));
    }

    @GET
    public List<ReceiptResponse> getbytag(@PathParam("tag") String tagName) {
        List<ReceiptsRecord> receiptsRecords = tags.getbyTag(tagName);

        return receiptsRecords.stream().map(ReceiptResponse::new).collect(toList());
    }
}