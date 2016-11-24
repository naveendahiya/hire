(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('CandidateJoborderStatusHistoryController', CandidateJoborderStatusHistoryController);

    CandidateJoborderStatusHistoryController.$inject = ['$scope', '$state', 'CandidateJoborderStatusHistory'];

    function CandidateJoborderStatusHistoryController ($scope, $state, CandidateJoborderStatusHistory) {
        var vm = this;

        vm.candidateJoborderStatusHistories = [];

        loadAll();

        function loadAll() {
            CandidateJoborderStatusHistory.query(function(result) {
                vm.candidateJoborderStatusHistories = result;
            });
        }
    }
})();
