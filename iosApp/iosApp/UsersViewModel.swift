import Foundation
import shared
import KMPNativeCoroutinesAsync

@MainActor
class ObservableUserStore: ObservableObject {
    @Published
    public var state: UsersState? = nil
    
    let store: UsersStateStore
    private var stateHandle: Task<(), Never>? = nil

    init() {
        self.store = UsersModule().usersStateStore

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
