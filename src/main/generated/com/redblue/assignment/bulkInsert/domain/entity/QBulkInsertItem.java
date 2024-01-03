package com.redblue.assignment.bulkInsert.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBulkInsertItem is a Querydsl query type for BulkInsertItem
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBulkInsertItem extends EntityPathBase<BulkInsertItem> {

    private static final long serialVersionUID = 1761535345L;

    public static final QBulkInsertItem bulkInsertItem = new QBulkInsertItem("bulkInsertItem");

    public final QAbstractEntity _super = new QAbstractEntity(this);

    public final NumberPath<Integer> amount = createNumber("amount", Integer.class);

    public final StringPath description = createString("description");

    public final StringPath name = createString("name");

    //inherited
    public final NumberPath<Long> seq = _super.seq;

    public QBulkInsertItem(String variable) {
        super(BulkInsertItem.class, forVariable(variable));
    }

    public QBulkInsertItem(Path<? extends BulkInsertItem> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBulkInsertItem(PathMetadata metadata) {
        super(BulkInsertItem.class, metadata);
    }

}

