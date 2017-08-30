package net.es.oscars.st.oper;

public class OperSM {

    private OperState state = OperState.ADMIN_DOWN_OPER_DOWN;

    public OperState next(OperEvent event) throws OperStateException {

        switch (state) {
            case ADMIN_DOWN_OPER_DOWN:
                switch (event) {
                    case ADMIN_UP:
                        return OperState.ADMIN_UP_OPER_DOWN;
                    case ADMIN_DOWN:
                        return OperState.ADMIN_DOWN_OPER_DOWN;
                    case OPER_UP:
                        return OperState.ADMIN_DOWN_OPER_UP;
                    case OPER_DOWN:
                        return OperState.ADMIN_DOWN_OPER_DOWN;
                }

            case ADMIN_DOWN_OPER_UP:
                switch (event) {
                    case ADMIN_UP:
                        return OperState.ADMIN_UP_OPER_UP;
                    case ADMIN_DOWN:
                        return OperState.ADMIN_DOWN_OPER_UP;
                    case OPER_UP:
                        return OperState.ADMIN_DOWN_OPER_UP;
                    case OPER_DOWN:
                        return OperState.ADMIN_DOWN_OPER_DOWN;
                }
            case ADMIN_UP_OPER_DOWN:
                switch (event) {
                    case ADMIN_UP:
                        return OperState.ADMIN_UP_OPER_DOWN;
                    case ADMIN_DOWN:
                        return OperState.ADMIN_DOWN_OPER_UP;
                    case OPER_UP:
                        return OperState.ADMIN_UP_OPER_UP;
                    case OPER_DOWN:
                        return OperState.ADMIN_UP_OPER_DOWN;
                }
            case ADMIN_UP_OPER_UP:
                switch (event) {
                    case ADMIN_UP:
                        return OperState.ADMIN_UP_OPER_UP;
                    case ADMIN_DOWN:
                        return OperState.ADMIN_DOWN_OPER_UP;
                    case OPER_UP:
                        return OperState.ADMIN_UP_OPER_UP;
                    case OPER_DOWN:
                        return OperState.ADMIN_UP_OPER_DOWN;
                }
            default:
                throw new OperStateException("illegal event "+event.toString()+" for state: "+state.toString());
        }
    }
}
