import { API_BASE_URL } from '../utils/constants'

function AuthSection({ token, setToken }) {

  const login = async () => {
    const popup = window.open(
      `${API_BASE_URL}/api/auth/login`,
      '_blank',
      'width=600,height=700'
    )

    const timer = setInterval(() => {
      try {
        if (!popup || popup.closed) {
          clearInterval(timer)
          return
        }

        const body = popup.document.body.innerText

        if (body.includes('jwt')) {
          const data = JSON.parse(body)

          localStorage.setItem('jwt', data.jwt)
          setToken(data.jwt)

          popup.close()
          clearInterval(timer)
        }
      } catch (e) {
        // ignore cross-origin issue until redirected
      }
    }, 1000)
  }

  const logout = () => {
    localStorage.removeItem('jwt')
    setToken('')
  }

  return (
    <div className="auth-box">
      <h2>Authorization</h2>

      {!token ? (
        <button className="primary-btn" onClick={login}>
          Authorize With Google
        </button>
      ) : (
        <>
          <p className="success">JWT Token Authorized</p>
          <button className="danger-btn" onClick={logout}>
            Logout
          </button>
        </>
      )}
    </div>
  )
}

export default AuthSection
