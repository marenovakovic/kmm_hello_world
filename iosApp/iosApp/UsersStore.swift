import shared
import KMPNativeCoroutinesAsync

@MainActor
class ObservableUserStore: ObservableObject {
    @Published
    public var state: UsersState? = nil
    
    let store: UsersStateStore = UsersModule().usersStateSource()
    private var stateHandle: Task<(), Never>? = nil

    init() {
        stateHandle = Task {
            do {
                let stream = asyncStream(for: store.stateNative)
                for try await state in stream {
                    self.state = state
                }
            } catch {
                print("Failed with error: \(error)")
            }
        }
    }

    func stopListeningState() {
        stateHandle?.cancel()
    }
}
