
import { atom } from 'recoil';
import { recoilPersist } from 'recoil-persist'
const { persistAtom } = recoilPersist()

const nameState = atom({
  key: 'nameState',
  default: null,
  effects_UNSTABLE: [persistAtom],
});

const logginState = atom({
  key: 'logginState',
  default: false,
  effects_UNSTABLE: [persistAtom],
})

const emailAuthState = atom({
  key: 'emailAuthState',
  default: false,
  effects_UNSTABLE: [persistAtom],
})
export { nameState, logginState, emailAuthState};