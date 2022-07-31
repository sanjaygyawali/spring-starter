package com.rasello.auth.services;

import com.rasello.auth.base.TableRequest;
import com.rasello.auth.base.TableResponse;
import com.rasello.auth.entity.Forms;

public interface FormService extends IBaseService<Forms> {
    Forms retrieveSchemaForResource(String schemaName);
    TableResponse<Forms> getDataList(TableRequest tableRequest);
}
