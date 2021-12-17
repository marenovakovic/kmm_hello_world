import SwiftUI
import shared

struct ContentView: View {
    let useCase = GetUsersUseCase()

    @ObservedObject
    var store: ObservableUserStore

    init() {
        store = ObservableUserStore(store: UsersStateStore(getUsersUseCase: useCase))
    }

    var body: some View {
        if store.loading {
            ProgressView()
                .transition(.opacity)
        } else {
            UsersList(users: store.users)
                .transition(.opacity)
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

class ObservableUserStore: ObservableObject {
    @Published
    public var loading: Bool = true
    @Published
    public var users: [User] = []

    let store: UsersStateStore
    var stateWatcher: Closeable?

    init(store: UsersStateStore) {
        self.store = store

        stateWatcher = self.store.watchState().watch { [weak self] state in
            if state is UsersState.Loading {
                self!.loading = true
            } else {
                self!.loading = false
                self!.users = (state as! UsersState.Users).users
            }
        }
    }

    deinit {
        stateWatcher?.close()
    }
}
