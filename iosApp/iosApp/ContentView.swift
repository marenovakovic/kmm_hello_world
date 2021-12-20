import SwiftUI
import shared

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
