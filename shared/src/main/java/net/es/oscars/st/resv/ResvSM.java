package net.es.oscars.st.resv;



public class ResvSM {

    private ResvState state = ResvState.IDLE_WAIT;

    public ResvState next(ResvEvent event) throws ResvStateException {
        String msg = "event: " + event.toString() + " arrived at state: " + state.toString();
        switch (state) {
            case IDLE_WAIT:
                switch (event) {
                    case UPSTREAM_SUBMIT_RECEIVED:
                        return ResvState.SUBMITTED;
                    default:
                        throw new ResvStateException("not allowed: "+msg);
                }
            case SUBMITTED:
                switch (event) {
                    case LOCAL_AND_DOWNSTREAM_CHECK_PASS:
                        return ResvState.HELD;
                    case ANY_CHECK_FAIL:
                        return ResvState.IDLE_WAIT;
                    default:
                        throw new ResvStateException("not allowed: "+msg);
                }

            case HELD:
                switch (event) {
                    case UPSTREAM_COMMIT_RECEIVED:
                        return ResvState.COMMITTING;
                    case UPSTREAM_COMMIT_TIMEOUT:
                        return ResvState.ABORTING;
                    case UPSTREAM_ABORT_RECEIVED:
                        return ResvState.ABORTING;
                    default:
                        throw new ResvStateException("not allowed: "+msg);
                }

            case COMMITTING:
                switch (event) {
                    case LOCAL_AND_DOWNSTREAM_COMMIT_PASS:
                        return ResvState.IDLE_WAIT;
                    case ANY_COMMIT_FAIL:
                        return ResvState.ABORTING;
                    case UPSTREAM_ABORT_RECEIVED:
                        return ResvState.ABORTING;
                    default:
                        throw new ResvStateException("not allowed: "+msg);
                }

            case ABORTING:
                switch (event) {
                    case LOCAL_AND_DOWNSTREAM_ABORT_PASS:
                        return ResvState.IDLE_WAIT;
                    case ANY_ABORT_FAIL:
                        return ResvState.ABORT_FAILED;
                    default:
                        throw new ResvStateException("not allowed: " + msg);
                }

            case ABORT_FAILED: {
                throw new ResvStateException("not allowed: " + msg);
            }


            default:
                throw new ResvStateException("not allowed: "+msg);
        }
    }
}
