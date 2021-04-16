package ntut.csie.sslab.ddd.usecase.cqrs;

import ntut.csie.sslab.ddd.usecase.Output;

public interface CqrsCommandOutput extends Output {

	String getId();

	CqrsCommandOutput setId(String id);

}
