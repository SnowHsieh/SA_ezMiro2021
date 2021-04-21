package ntut.csie.sslab.ddd.usecase.cqrs;


import ntut.csie.sslab.ddd.usecase.Input;
import ntut.csie.sslab.ddd.usecase.Output;
import ntut.csie.sslab.ddd.usecase.UseCase;

public interface Query<I extends Input, O extends Output> extends UseCase<I, O> {
	I newInput();
}
