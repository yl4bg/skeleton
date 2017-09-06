package dao;

import generated.tables.records.TagsRecord;
import generated.tables.records.ReceiptsRecord;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

import java.math.BigDecimal;
import java.util.List;

import static com.google.common.base.Preconditions.checkState;
import static generated.Tables.RECEIPTS;
import static generated.Tables.TAGS;

public class TagDao {
    DSLContext dsl;

    public TagDao(Configuration jooqConfig) {
        this.dsl = DSL.using(jooqConfig);
    }

    public List<ReceiptsRecord> getbyTag(String tag) {
        return dsl.selectFrom(RECEIPTS).where(RECEIPTS.ID.in(dsl.select(TAGS.RECEIPT_ID).from(TAGS).
                where(TAGS.TAG.eq(tag)).fetch())).fetch();
    }

    public int toggle(String tag, int id) {
        if (dsl.selectFrom(RECEIPTS).where(RECEIPTS.ID.eq(id)).fetchOne() != null) {
            if (dsl.selectFrom(TAGS).where(TAGS.RECEIPT_ID.eq(id).and(TAGS.TAG.eq(tag))).fetchOne() == null) {
                dsl.insertInto(TAGS).values(id, tag).execute();
                return 1;
            }
            else {
                dsl.deleteFrom(TAGS).where(TAGS.RECEIPT_ID.eq(id).and(TAGS.TAG.eq(tag)));
                return 2;
            }
        }
        return 3;
    }
}