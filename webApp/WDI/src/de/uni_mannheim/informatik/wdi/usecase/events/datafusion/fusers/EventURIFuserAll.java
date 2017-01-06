package de.uni_mannheim.informatik.wdi.usecase.events.datafusion.fusers;

import de.uni_mannheim.informatik.wdi.datafusion.AttributeValueFuser;
import de.uni_mannheim.informatik.wdi.datafusion.conflictresolution.list.Union;
import de.uni_mannheim.informatik.wdi.model.*;
import de.uni_mannheim.informatik.wdi.usecase.events.model.Event;

import java.util.List;


/**
 * {@link AttributeValueFuser} for the URIs of {@link Event}s.
 * based on ActorFuserUnion
 * created on 2017-01-06
 * @author Daniel Ringler
 *
 */
public class EventURIFuserAll extends
        AttributeValueFuser<List<String>, Event, DefaultSchemaElement> {


    public EventURIFuserAll() {
        super(new Union<String, Event, DefaultSchemaElement>());
    }

    @Override
    public boolean hasValue(Event record, Correspondence<DefaultSchemaElement, Event> correspondence) {
        return record.hasValue(Event.URIS);
    }

    @Override
    protected List<String> getValue(Event record, Correspondence<DefaultSchemaElement, Event> correspondence) {
        return record.getUris();
    }

    @Override
    public void fuse(RecordGroup<Event, DefaultSchemaElement> group, Event fusedRecord, ResultSet<Correspondence<DefaultSchemaElement, Event>> schemaCorrespondences, DefaultSchemaElement schemaElement) {

        // get the fused value
        FusedValue<List<String>, Event, DefaultSchemaElement> fused = getFusedValue(group, schemaCorrespondences, schemaElement);

        // set the value for the fused record
        fusedRecord.setURIs(fused.getValue());

        // add provenance info
        fusedRecord.setAttributeProvenance(Event.URIS, fused.getOriginalIds());
    }
}
