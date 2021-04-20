package ntut.csie.sslab.account.users.query.usecase.user.get;

import ntut.csie.sslab.account.users.query.usecase.UserQueryRepository;
import ntut.csie.sslab.account.users.query.usecase.UserDto;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;

public class GetUserUseCaseImpl implements GetUserUseCase{

    private UserQueryRepository userRepository;

    public GetUserUseCaseImpl(UserQueryRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public void execute(GetUserInput input, GetUserOutput output) {
        UserDto user = userRepository.findById(input.getUserId()).orElse(null);

        if (null == user){
            output.setId(input.getUserId())
                    .setExitCode(ExitCode.FAILURE)
                    .setMessage("Get user failed: user not found, user id = " + input.getUserId());
            return;
        }

        output.setUser(user);
        output.setExitCode(ExitCode.SUCCESS);
    }

    @Override
    public GetUserInput newInput() {
        return new GetUserInputImpl();
    }

    private static class GetUserInputImpl implements GetUserInput{
        private String userId;


        @Override
        public String getUserId() {
            return userId;
        }

        @Override
        public void setUserId(String userId) {
            this.userId = userId;
        }
    }

}
