import { defineStore } from 'pinia'

export const usePatientViewStore = defineStore('patientView', {
  state: () => ({
    currentView: null,     
    selectedPatientUuid: null,
    selectedItem: null,
    name:null,
  }),
  actions: {
    setVisitView({ patientUuid,name }) {
      this.selectedPatientUuid = patientUuid
      this.name = name
      this.currentView = 'visit'
    },
    setPaymentView(item) {
      this.selectedItem = item
      this.currentView = 'payment'
    },
    reset() {
      this.currentView = null
      this.selectedPatientUuid = null
      this.name = null
      this.selectedItem = null
    }
  }
})
