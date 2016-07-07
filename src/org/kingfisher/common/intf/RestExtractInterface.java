package org.kingfisher.common.intf;

import org.kingfisher.common.model.BaseRestServiceEntity;
import org.kingfisher.common.model.RestExtractorException;

import java.io.File;
import java.util.Collection;

// TODO: 06.07.2016 добавить полное описание
public interface RestExtractInterface {
    Collection<BaseRestServiceEntity> extract(final File xml, final ClassLoader classLoader) throws RestExtractorException;
}
