import SwiftUI
import shared
import KMPNativeCoroutinesAsync

struct ContentView: View {
    @ObservedObject
    var store: ObservableUserStore

    init() {
        store = ObservableUserStore()
    }

    var body: some View {
        if store.state == nil || store.state is UsersState.Loading {
            ProgressView()
                .transition(.opacity)
        } else {
            UsersList(users: (store.state as! UsersState.Users).users)
                .transition(.opacity)
                .onDisappear(perform: { store.stopListeningState() })
        }
    }
}

struct UsersList: View {
    var users: [User]
    
    var body: some View {
        List(users, id: \.id) { user in
            UserView(user: user)
        }
    }
}

struct UserView: View {
    var user: User
    
    var body: some View {
        VStack(alignment: .leading) {
            Text(user.name)
                .font(.title3)
            Text(user.username)
            Text(user.email)
        }
    }
}

@MainActor
class ObservableUserStore: ObservableObject {
    @Published
    public var state: UsersState? = nil
    
    let store: UsersStateStore
    private var stateHandle: Task<(), Never>? = nil

    init() {
        let useCase = GetUsersUseCase()
        self.store = UsersStateStore(getUsersUseCase: useCase)

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
